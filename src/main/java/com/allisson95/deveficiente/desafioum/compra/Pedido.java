package com.allisson95.deveficiente.desafioum.compra;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private final Compra compra;

    @Size(min = 1)
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "pedidos_itens", joinColumns = { @JoinColumn(name = "pedido_id") })
    private final Set<@Valid ItemPedido> itens = new HashSet<>();

    @Transient
    private BigDecimal total = BigDecimal.ZERO;

    /**
     * @deprecated para uso exclusivo do Hibernate
     */
    @Deprecated(since = "1.0")
    Pedido() {
        this.compra = null;
    }

    public Pedido(
            @NotNull @Valid final Compra compra,
            @Size(min = 1) @NotEmpty final Set<@Valid ItemPedido> itens,
            @NotNull @Positive final BigDecimal total) {
        Objects.requireNonNull(compra, "Compra não pode ser nula");
        Objects.requireNonNull(itens, "A lista de itens não pode ser nula");
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("A lista de itens não pode ser vazia");
        }

        this.compra = compra;
        this.itens.addAll(itens);

        this.calcularTotal();
        if (this.total.compareTo(total) != 0) {
            throw new IllegalArgumentException("O total do pedido não corresponde ao total informado");
        }
    }

    @PostLoad
    void postLoad() {
        this.calcularTotal();
    }

    private void calcularTotal() {
        this.total = this.itens.stream()
                .map(ItemPedido::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
