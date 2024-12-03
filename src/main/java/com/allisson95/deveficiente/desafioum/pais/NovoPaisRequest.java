package com.allisson95.deveficiente.desafioum.pais;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Unique;

import jakarta.validation.constraints.NotBlank;

public record NovoPaisRequest(
        @NotBlank @Unique(entity = Pais.class, field = "nome") String nome) {

    public Pais toModel() {
        return new Pais(this.nome);
    }

}
