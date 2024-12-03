package com.allisson95.deveficiente.desafioum.categoria;

import java.util.UUID;

import com.allisson95.deveficiente.desafioum.comum.exceptions.NotFoundException;

public class CategoriaNaoEncontradaException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public CategoriaNaoEncontradaException(final UUID id) {
        super("Não foi possível encontrar a categoria com o id " + id);
    }

}
