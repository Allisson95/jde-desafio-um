package com.allisson95.deveficiente.desafioum.livro;

public record ListaLivrosResponse(
        String id,
        String titulo) {

    public static ListaLivrosResponse of(final Livro livro) {
        return new ListaLivrosResponse(livro.getId().toString(), livro.getTitulo());
    }

}
