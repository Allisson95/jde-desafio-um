package com.allisson95.deveficiente.desafioum.compra;

import com.allisson95.deveficiente.desafioum.comum.exceptions.NotFoundException;

public class CompraNaoEncontradaException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public CompraNaoEncontradaException(final String id) {
        super("Não foi possível encontrar uma compra com o id " + id);
    }

}
