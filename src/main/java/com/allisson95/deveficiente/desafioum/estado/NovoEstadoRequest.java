package com.allisson95.deveficiente.desafioum.estado;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Unique;
import com.allisson95.deveficiente.desafioum.pais.Pais;

import jakarta.validation.constraints.NotBlank;

public record NovoEstadoRequest(
        @NotBlank @Unique(entity = Estado.class, field = "nome") String nome) {

    public Estado toModel(final Pais pais) {
        return new Estado(this.nome, pais);
    }

}
