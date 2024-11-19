package com.allisson95.deveficiente.desafioum.categoria;

public class CategoriaExistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoriaExistenteException(final String nome) {
        super("Já existe uma categoria com o nome " + nome, null, true, false);
    }

}
