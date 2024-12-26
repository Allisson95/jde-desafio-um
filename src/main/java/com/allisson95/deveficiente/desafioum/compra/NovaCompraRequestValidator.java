package com.allisson95.deveficiente.desafioum.compra;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.allisson95.deveficiente.desafioum.cupomdesconto.CuponsDescontoRepository;
import com.allisson95.deveficiente.desafioum.estado.Estado;
import com.allisson95.deveficiente.desafioum.pais.Pais;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;

@Transactional(readOnly = true)
@Component
public class NovaCompraRequestValidator implements Validator {

    private final EntityManager entityManager;
    private final CuponsDescontoRepository cuponsDescontoRepository;

    public NovaCompraRequestValidator(
            final EntityManager entityManager,
            final CuponsDescontoRepository cuponsDescontoRepository) {
        this.entityManager = entityManager;
        this.cuponsDescontoRepository = cuponsDescontoRepository;
    }

    @Override
    public boolean supports(final @NonNull Class<?> clazz) {
        return clazz.isAssignableFrom(NovaCompraRequest.class);
    }

    @Override
    public void validate(final @NonNull Object target, final @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        final var request = (NovaCompraRequest) target;
        this.verificarSeEstadoPertenceAoPais(errors, request);
        this.verificarCupomDescontoEhValido(errors, request);
    }

    private void verificarSeEstadoPertenceAoPais(final Errors errors, final NovaCompraRequest request) {
        @NotNull
        final Pais pais = this.entityManager.find(Pais.class, request.paisId());

        if (pais.temEstados() && request.estadoId() == null) {
            errors.rejectValue(
                    "estadoId",
                    "estadoId.obrigatorio",
                    "Informe um estado para o pais " + pais.getNome());
            return;
        }

        if (request.estadoId() == null) {
            return;
        }

        @NotNull
        final Estado estado = this.entityManager.find(Estado.class, request.estadoId());
        if (!estado.pertenceAoPais(pais)) {
            errors.rejectValue(
                    "estadoId",
                    "estadoId.invalido",
                    "O estado " + estado.getNome() + " não pertence ao pais " + pais.getNome());
        }
    }

    private void verificarCupomDescontoEhValido(final Errors errors, final NovaCompraRequest request) {
        request.getCodigoCupom()
                .flatMap(this.cuponsDescontoRepository::findByCodigo)
                .ifPresent(cupomDesconto -> {
                    if (!cupomDesconto.isValido()) {
                        errors.rejectValue(
                                "codigoCupom",
                                "codigoCupom.invalido",
                                "O cupom de desconto informado não é válido");
                    }
                });
    }

}
