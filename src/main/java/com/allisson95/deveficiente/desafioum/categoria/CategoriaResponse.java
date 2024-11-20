package com.allisson95.deveficiente.desafioum.categoria;

public record CategoriaResponse(String id) {

    public static CategoriaResponse of(final Categoria categoria) {
        return new CategoriaResponse(categoria.getId().toString());
    }

}
