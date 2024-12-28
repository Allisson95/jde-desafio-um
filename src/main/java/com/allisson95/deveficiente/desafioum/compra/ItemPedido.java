package com.allisson95.deveficiente.desafioum.compra;

import java.math.BigDecimal;
import java.util.Objects;

import com.allisson95.deveficiente.desafioum.livro.Livro;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Embeddable
public class ItemPedido {

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private final Livro livro;

    @NotNull
    @Positive
    private final Integer quantidade;

    @NotNull
    @Positive
    private final BigDecimal precoUnitario;

    /**
     * @deprecated para uso exclusivo do Hibernate
     */
    @Deprecated(since = "1.0")
    ItemPedido() {
        this.livro = null;
        this.quantidade = null;
        this.precoUnitario = null;
    }

    public ItemPedido(@NotNull @Valid final Livro livro, @NotNull @Positive final Integer quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
        this.precoUnitario = livro.getPreco();
    }

    public BigDecimal total() {
        return this.precoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
    }

    public Livro getLivro() {
        return this.livro;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return this.precoUnitario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.livro);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final ItemPedido other = (ItemPedido) obj;
        return Objects.equals(this.livro, other.livro);
    }

}
