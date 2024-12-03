package com.allisson95.deveficiente.desafioum.pais;

public record PaisResponse(String id) {

    public static PaisResponse of(final Pais pais) {
        return new PaisResponse(pais.getId().toString());
    }

}
