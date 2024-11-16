package com.allisson95.deveficiente.desafioum.autor;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Autor {

    @Id
    private UUID id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 400)
    private String descricao;

    private Instant criadoEm;

    @Deprecated
    Autor() {
    }

    public Autor(final String nome, final String email, final String descricao) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.criadoEm = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

}