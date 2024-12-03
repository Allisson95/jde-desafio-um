package com.allisson95.deveficiente.desafioum.pais;

import com.allisson95.deveficiente.desafioum.comum.exceptions.NotFoundException;

public class PaisNaoEncontradoException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public PaisNaoEncontradoException(final String id) {
        super("Não foi possível encontrar um país com o id " + id);
    }

}
