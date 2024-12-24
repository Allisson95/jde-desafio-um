package com.allisson95.deveficiente.desafioum.compra;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.allisson95.deveficiente.desafioum.estado.EstadoRepository;
import com.allisson95.deveficiente.desafioum.pais.Pais;
import com.allisson95.deveficiente.desafioum.pais.PaisRepository;

@Transactional(readOnly = true)
@Component
public class NovaCompraRequestValidator implements Validator {

    private final EstadoRepository estadoRepository;
    private final PaisRepository paisRepository;

    public NovaCompraRequestValidator(final EstadoRepository estadoRepository, final PaisRepository paisRepository) {
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
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

        final Pais pais = this.paisRepository.findById(request.paisId()).get();

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

        this.estadoRepository.findById(request.estadoId()).ifPresent(estado -> {
            if (!estado.pertenceAoPais(pais)) {
                errors.rejectValue(
                        "estadoId",
                        "estadoId.invalido",
                        "O estado " + estado.getNome() + " não pertence ao pais " + pais.getNome());
            }
        });
    }

}
