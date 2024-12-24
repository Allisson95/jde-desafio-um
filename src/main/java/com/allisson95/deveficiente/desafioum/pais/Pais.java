package com.allisson95.deveficiente.desafioum.pais;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.allisson95.deveficiente.desafioum.estado.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais", orphanRemoval = true)
    private final List<@Valid Estado> estados = new ArrayList<>();

    @Deprecated
    Pais() {
    }

    public Pais(final String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    public UUID getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public boolean temEstados() {
        return !this.estados.isEmpty();
    }

}
