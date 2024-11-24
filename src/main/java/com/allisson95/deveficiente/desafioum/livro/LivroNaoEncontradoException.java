package com.allisson95.deveficiente.desafioum.livro;

public class LivroNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LivroNaoEncontradoException(final String id) {
        super("Não foi possível encontrar o livro com o id " + id, null, true, false);
    }

}
