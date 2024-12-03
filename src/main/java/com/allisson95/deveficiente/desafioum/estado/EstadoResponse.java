package com.allisson95.deveficiente.desafioum.estado;

public record EstadoResponse(String id) {

    public static EstadoResponse of(final Estado estado) {
        return new EstadoResponse(estado.getId().toString());
    }

}
