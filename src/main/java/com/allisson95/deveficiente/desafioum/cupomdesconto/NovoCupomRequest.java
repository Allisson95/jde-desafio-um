package com.allisson95.deveficiente.desafioum.cupomdesconto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Unique;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NovoCupomRequest(
        @NotBlank @Unique(entity = CupomDesconto.class, field = "codigo") String codigo,
        @NotNull @Positive BigDecimal percentualDesconto,
        @NotNull @Future LocalDate validade) {

    public CupomDesconto toModel() {
        return new CupomDesconto(this.codigo, this.percentualDesconto, this.validade);
    }

}
