package com.allisson95.deveficiente.desafioum.categoria;

import java.util.UUID;

public class CategoriaNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoriaNaoEncontradaException(final UUID id) {
        super("Não foi possível encontrar a categoria com o id " + id, null, true, false);
    }

}
