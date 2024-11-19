package com.allisson95.deveficiente.desafioum.categoria;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Unique;

import jakarta.validation.constraints.NotBlank;

public record NovaCategoriaRequest(
        @NotBlank @Unique(entity = Categoria.class, field = "nome") String nome) {

    public Categoria toModel() {
        return new Categoria(this.nome);
    }

}
