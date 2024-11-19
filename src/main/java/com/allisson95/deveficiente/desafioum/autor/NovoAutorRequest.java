package com.allisson95.deveficiente.desafioum.autor;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Unique;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovoAutorRequest(
        @NotBlank String nome,
        @NotBlank @Email @Unique(entity = Autor.class, field = "email") String email,
        @NotBlank @Size(max = 400) String descricao) {

    public Autor toModel() {
        return new Autor(this.nome, this.email, this.descricao);
    }

}
