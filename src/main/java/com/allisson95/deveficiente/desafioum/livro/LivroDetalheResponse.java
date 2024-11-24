package com.allisson95.deveficiente.desafioum.livro;

import java.math.BigDecimal;

public record LivroDetalheResponse(
        String titulo,
        String resumo,
        String sumario,
        BigDecimal preco,
        Integer paginas,
        String isbn,
        LivroDetalheAutorResponse autor) {

    public static LivroDetalheResponse of(final Livro livro) {
        return new LivroDetalheResponse(
                livro.getTitulo(),
                livro.getResumo(),
                livro.getSumario(),
                livro.getPreco(),
                livro.getPaginas(),
                livro.getIsbn(),
                LivroDetalheAutorResponse.of(livro.getAutor()));
    }

}
