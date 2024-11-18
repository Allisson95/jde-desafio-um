package com.allisson95.deveficiente.desafioum.autor;

public class AutorExistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutorExistenteException(final String email) {
        super("Já existe um autor(a) com o email " + email, null, true, false);
    }

}
