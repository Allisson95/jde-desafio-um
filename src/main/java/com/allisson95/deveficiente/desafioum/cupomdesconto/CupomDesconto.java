package com.allisson95.deveficiente.desafioum.cupomdesconto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "cupons_desconto")
public class CupomDesconto {

    @Id
    private final UUID id;

    @NotBlank
    private final String codigo;

    @NotNull
    @Positive
    private final BigDecimal percentualDesconto;

    @NotNull
    @Future
    private final LocalDate validade;

    /**
     * @deprecated Para uso exclusivo do Hibernate
     */
    @Deprecated(since = "1.0")
    CupomDesconto() {
        this.id = null;
        this.codigo = null;
        this.percentualDesconto = null;
        this.validade = null;
    }

    public CupomDesconto(
            @NotBlank final String codigo,
            @NotNull @Positive final BigDecimal percentualDesconto,
            @NotNull @Future final LocalDate validade) {
        this.id = UUID.randomUUID();
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validade = validade;
    }

    public boolean isValido() {
        return LocalDate.now().compareTo(this.validade) <= 0;
    }

    public BigDecimal getPercentualDesconto() {
        return this.percentualDesconto;
    }

    public LocalDate getValidade() {
        return this.validade;
    }

}
