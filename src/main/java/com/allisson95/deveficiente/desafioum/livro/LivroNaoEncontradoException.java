package com.allisson95.deveficiente.desafioum.livro;

import com.allisson95.deveficiente.desafioum.comum.exceptions.NotFoundException;

public class LivroNaoEncontradoException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public LivroNaoEncontradoException(final String id) {
        super("Não foi possível encontrar o livro com o id " + id);
    }

}
