package com.allisson95.deveficiente.desafioum.configuracoes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.allisson95.deveficiente.desafioum.autor.AutorExistenteException;
import com.allisson95.deveficiente.desafioum.categoria.CategoriaExistenteException;
import com.allisson95.deveficiente.desafioum.comum.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "Ocorreu um erro inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    @ExceptionHandler({ NotFoundException.class })
    @Nullable
    protected ResponseEntity<Object> handleNotFoundException(
            final @NonNull NotFoundException ex,
            final @NonNull WebRequest request) {
        final var status = HttpStatus.NOT_FOUND;
        final var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());

        return this.handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, status, request);
    }

    @ExceptionHandler({ AutorExistenteException.class })
    @Nullable
    protected ResponseEntity<Object> handleAutorExistente(
            final @NonNull AutorExistenteException ex,
            final @NonNull WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problem = ProblemDetail.forStatusAndDetail(status, "Corpo da requisição inválido.");
        problem.setProperty("errors", Map.of("detail", ex.getMessage(), "path", "$.email"));

        return this.handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, status, request);
    }

    @ExceptionHandler({ CategoriaExistenteException.class })
    @Nullable
    protected ResponseEntity<Object> handleAutorExistente(
            final @NonNull CategoriaExistenteException ex,
            final @NonNull WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problem = ProblemDetail.forStatusAndDetail(status, "Corpo da requisição inválido.");
        problem.setProperty("errors", Map.of("detail", ex.getMessage(), "path", "$.nome"));

        return this.handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, status, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final @NonNull HttpRequestMethodNotSupportedException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        return this.handleExceptionInternal(ex, ex.getBody(), headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final @NonNull HttpMessageNotReadableException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        final var rootCause = ex.getCause();

        if (rootCause instanceof final PropertyBindingException propertyBindingException) {
            return this.handlePropertyBinding(propertyBindingException, headers, status, request);
        }

        if (rootCause instanceof final MismatchedInputException mismatchedInputException) {
            return this.handleInvalidFormat(mismatchedInputException, headers, status, request);
        }

        final var problem = ProblemDetail.forStatusAndDetail(status, "Corpo da requisição inválido.");

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            final @NonNull HttpMediaTypeNotAcceptableException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final @NonNull MethodArgumentNotValidException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        return this.handleValidationException(ex, headers, status, request, ex.getBindingResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final @NonNull NoHandlerFoundException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        final var detail = "O recurso " + ex.getHttpMethod().toUpperCase() + " " + ex.getRequestURL() + " não existe.";
        final var problem = ProblemDetail.forStatusAndDetail(status, detail);
        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override

    @Nullable
    protected ResponseEntity<Object> handleTypeMismatch(
            final @NonNull TypeMismatchException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        if (ex instanceof final MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return this.handleMethodArgumentTypeMismatchException(
                    methodArgumentTypeMismatchException,
                    headers,
                    status,
                    request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleUncaught(final Exception ex, final WebRequest request) {
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        final var problem = ProblemDetail.forStatusAndDetail(status,
                GlobalExceptionHandler.DEFAULT_ERROR_MESSAGE);
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleExceptionInternal(
            final @NonNull Exception ex,
            @Nullable Object body,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatusCode status,
            final @NonNull WebRequest request) {
        this.logger.error(ex.getMessage(), ex);

        if (body == null) {
            body = ProblemDetail.forStatusAndDetail(status,
                    GlobalExceptionHandler.DEFAULT_ERROR_MESSAGE);
        } else if (body instanceof final String detail) {
            body = ProblemDetail.forStatusAndDetail(status, detail);
        }

        final var writableHttpHeaders = new HttpHeaders(headers);
        writableHttpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            final MismatchedInputException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        final var path = this.joinReferences(ex.getPath());
        final var expectedType = this.getExpectedType(ex.getTargetType());

        final var detail = "A propriedade recebeu o valor de um tipo inválido. Corrija e informe um valor compatível com o tipo: "
                + expectedType;

        final var problem = ProblemDetail.forStatusAndDetail(status, "Propriedade inválida.");
        problem.setProperty("errors", Map.of("detail", detail, "path", path));

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        final var expectedType = this.getExpectedType(ex.getRequiredType());
        final var detail = "O parâmetro recebeu o valor de um tipo inválido. Corrija e informe um valor compatível com o tipo especificado na documentação da api: "
                + expectedType;

        final var isPathVariable = ex.getParameter().getParameterAnnotation(PathVariable.class) != null;

        final var errors = new HashMap<String, Object>();
        errors.put("detail", detail);
        if (isPathVariable) {
            errors.put("path", ex.getName());
        } else {
            errors.put("parameter", ex.getName());
        }

        final var problem = ProblemDetail.forStatusAndDetail(status, "Propriedade inválida.");
        problem.setProperty("errors", errors);

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(
            final PropertyBindingException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        final var path = this.joinReferences(ex.getPath());
        final var detail = "A propriedade não existe.";

        final var problem = ProblemDetail.forStatusAndDetail(status, "Propriedade inválida.");
        problem.setProperty("errors", Map.of("detail", detail, "path", path));

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationException(
            final Exception ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request,
            final BindingResult bindingResult) {
        final var errors = bindingResult.getAllErrors().stream()
                .map(error -> {
                    final var message = error.getDefaultMessage();
                    var path = error.getObjectName();

                    if (error instanceof final FieldError fieldError) {
                        path = fieldError.getField();
                    }

                    return Map.of("detail", message, "path", "$." + path);
                })
                .toList();

        final var problem = ProblemDetail.forStatusAndDetail(status, "Propriedade inválida.");
        problem.setProperty("errors", errors);

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String getExpectedType(final Class<?> clazz) {
        String expectedType = "especificado na documentação da api";

        if (clazz == null) {
            return expectedType;
        }

        if (String.class.isAssignableFrom(clazz)) {
            expectedType = "'string'";
        } else if (Number.class.isAssignableFrom(clazz)) {
            expectedType = "'number'";
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            expectedType = "'boolean'";
        } else if (Collection.class.isAssignableFrom(clazz)) {
            expectedType = "'array'";
        }

        return expectedType;
    }

    private String joinReferences(final List<Reference> references) {
        return Optional.ofNullable(references).orElse(List.of())
                .stream()
                .collect(new ToJsonPathCollector());
    }

    private static class ToJsonPathCollector implements Collector<Reference, List<String>, String> {

        @Override
        public Supplier<List<String>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<String>, Reference> accumulator() {
            return (refs, reference) -> {
                if (reference.getFrom() instanceof Collection) {
                    this.addCollectionReference(
                            refs,
                            reference,
                            ref -> String.valueOf(ref.getIndex()));
                } else if (reference.getFrom() instanceof Map) {
                    this.addCollectionReference(
                            refs,
                            reference,
                            Reference::getFieldName);
                } else {
                    refs.add(reference.getFieldName());
                }
            };
        }

        @Override
        public BinaryOperator<List<String>> combiner() {
            return (l, r) -> {
                l.addAll(r);
                return l;
            };
        }

        @Override
        public Function<List<String>, String> finisher() {
            return refs -> refs.stream()
                    .collect(Collectors.joining(".", "$.", ""));
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }

        private void addCollectionReference(
                final List<String> references,
                final Reference reference,
                final Function<Reference, String> collectionIndexFn) {
            final var collectionReferenceAsString = collectionIndexFn.apply(reference);
            if (references.isEmpty()) {
                references.add("[" + collectionReferenceAsString + "]");
            } else {
                final var lastRefIndex = references.size() - 1;
                final var lastRefValue = references.get(lastRefIndex);
                final var collectionRef = lastRefValue + "[" + collectionReferenceAsString + "]";
                references.set(lastRefIndex, collectionRef);
            }
        }

    }

}
