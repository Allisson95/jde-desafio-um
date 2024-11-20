package com.allisson95.deveficiente.desafioum.autor;

public record AutorResponse(String id) {

    public static AutorResponse of(final Autor autor) {
        return new AutorResponse(autor.getId().toString());
    }

}
