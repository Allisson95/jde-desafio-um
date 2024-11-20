package com.allisson95.deveficiente.desafioum.livro;

public record LivroResponse(String id) {

    public static LivroResponse of(final Livro livro) {
        return new LivroResponse(livro.getId().toString());
    }

}
