package com.allisson95.deveficiente.desafioum.compra;

import java.math.BigDecimal;
import java.util.UUID;

import com.allisson95.deveficiente.desafioum.estado.Estado;

public record CompraResponse(
        UUID id,
        String email,
        String nome,
        String sobrenome,
        String documento,
        String cep,
        String endereco,
        String complemento,
        String cidade,
        UUID paisId,
        UUID estadoId,
        String telefone,
        BigDecimal total,
        boolean cupomDescontoAplicado,
        BigDecimal totalComDesconto,
        ItemPedidoResponse[] itensPedido) {

    public CompraResponse(final Compra compra) {
        this(compra.getId(),
                compra.getEmail(),
                compra.getNome(),
                compra.getSobrenome(),
                compra.getDocumento(),
                compra.getCep(),
                compra.getEndereco(),
                compra.getComplemento(),
                compra.getCidade(),
                compra.getPais().getId(),
                compra.getEstado().map(Estado::getId).orElse(null),
                compra.getTelefone(),
                compra.total(),
                compra.getCupomDescontoAplicado().isPresent(),
                compra.totalComDesconto(),
                compra.getPedido().getItens().stream().map(ItemPedidoResponse::new).toArray(ItemPedidoResponse[]::new));
    }

    public record ItemPedidoResponse(
            UUID livroId,
            int quantidade,
            BigDecimal preco) {

        public ItemPedidoResponse(final ItemPedido itemPedido) {
            this(itemPedido.getLivro().getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario());
        }
    }

}
