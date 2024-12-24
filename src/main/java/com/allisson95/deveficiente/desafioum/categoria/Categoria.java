package com.allisson95.deveficiente.desafioum.categoria;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(name = "uk_categorias_nome", columnNames = "nome"))
public class Categoria {

    @Id
    private UUID id;

    @NotBlank
    private String nome;

    @Deprecated
    Categoria() {
    }

    public Categoria(final String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    public UUID getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

}
