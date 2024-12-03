package com.allisson95.deveficiente.desafioum.comum.exceptions;

public abstract class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected NotFoundException(final String message) {
        super(message, null, true, false);
    }

}
