package com.allisson95.deveficiente.desafioum.pais;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "paises", uniqueConstraints = {
        @UniqueConstraint(name = "uk_paises_nome", columnNames = "nome")
})
public class Pais {

    @Id
    private UUID id;

    @NotBlank
    private String nome;

    @Deprecated
    Pais() {
    }

    public Pais(final String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
