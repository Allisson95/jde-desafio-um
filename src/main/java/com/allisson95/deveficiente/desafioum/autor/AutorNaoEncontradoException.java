package com.allisson95.deveficiente.desafioum.autor;

import java.util.UUID;

import com.allisson95.deveficiente.desafioum.comum.exceptions.NotFoundException;

public class AutorNaoEncontradoException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public AutorNaoEncontradoException(final UUID id) {
        super("Não foi possível encontrar o autor com o id " + id);
    }

}
