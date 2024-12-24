package com.allisson95.deveficiente.desafioum.compra;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Documento;
import com.allisson95.deveficiente.desafioum.configuracoes.validation.ExistsById;
import com.allisson95.deveficiente.desafioum.estado.Estado;
import com.allisson95.deveficiente.desafioum.livro.Livro;
import com.allisson95.deveficiente.desafioum.pais.Pais;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record NovaCompraRequest(
        @NotBlank @Email String email,
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank @Documento String documento,
        @NotBlank String cep,
        @NotBlank String endereco,
        @NotBlank String complemento,
        @NotBlank String cidade,
        @NotNull @ExistsById(entity = Pais.class) UUID paisId,
        @ExistsById(entity = Estado.class) UUID estadoId,
        @NotBlank String telefone,
        @NotNull @Valid PedidoRequest pedido) {

    public Compra toModel(final EntityManager entityManager) {
        final Pais pais = entityManager.getReference(Pais.class, this.paisId());
        final Estado estado = Optional.ofNullable(this.estadoId())
                .map(estadoId -> entityManager.getReference(Estado.class, estadoId))
                .orElse(null);

        final Function<Compra, Pedido> pedidoFactoryFn = compra -> this.pedido().toModel(compra, entityManager);

        return new Compra(
                this.email,
                this.nome,
                this.sobrenome,
                this.documento,
                this.cep,
                this.endereco,
                this.complemento,
                this.cidade,
                pais,
                estado,
                this.telefone,
                pedidoFactoryFn);
    }

    public record PedidoRequest(
            @NotNull @Positive BigDecimal total,
            @NotEmpty @Size(min = 1) List<@Valid ItemPedidoRequest> itens) {

        public Pedido toModel(final Compra compra, final EntityManager entityManager) {
            @NotEmpty
            @Size(min = 1)
            final Set<ItemPedido> itens = this.itens().stream()
                    .map(item -> item.toModel(entityManager))
                    .collect(Collectors.toSet());

            return new Pedido(compra, itens, this.total());
        }

        public record ItemPedidoRequest(
                @NotNull @ExistsById(entity = Livro.class) UUID livroId,
                @NotNull @Positive Integer quantidade) {

            public ItemPedido toModel(final EntityManager entityManager) {
                @NotNull
                final Livro livro = entityManager.getReference(Livro.class, this.livroId());
                return new ItemPedido(livro, this.quantidade());
            }
        }

    }

}
