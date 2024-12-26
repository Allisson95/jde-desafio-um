package com.allisson95.deveficiente.desafioum.compra;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.allisson95.deveficiente.desafioum.cupomdesconto.CupomDesconto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Embeddable
public class CupomDescontoAplicado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cupom_desconto_id")
    private final CupomDesconto cupomDesconto;

    @NotNull
    @Positive
    @Column(name = "cupom_desconto_percentual")
    private final BigDecimal percentualDesconto;

    @NotNull
    @Future
    @Column(name = "cupom_desconto_validade")
    private final LocalDate validade;

    /**
     * @deprecated para uso exclusivo do Hibernate
     */
    @Deprecated(since = "1.0")
    CupomDescontoAplicado() {
        this.cupomDesconto = null;
        this.percentualDesconto = null;
        this.validade = null;
    }

    public CupomDescontoAplicado(@NotNull @Valid final CupomDesconto cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
        this.percentualDesconto = cupomDesconto.getPercentualDesconto();
        this.validade = cupomDesconto.getValidade();
    }

}
