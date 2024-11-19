package com.allisson95.deveficiente.desafioum.autor;

import java.util.UUID;

public class AutorNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutorNaoEncontradoException(final UUID id) {
        super("Não foi possível encontrar o autor com o id " + id, null, true, false);
    }

}
