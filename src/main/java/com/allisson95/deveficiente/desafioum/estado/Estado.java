package com.allisson95.deveficiente.desafioum.estado;

import java.util.UUID;

import com.allisson95.deveficiente.desafioum.pais.Pais;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "estados", uniqueConstraints = {
        @UniqueConstraint(name = "uk_estados_nome", columnNames = "nome")
})
public class Estado {

    @Id
    private UUID id;

    @NotBlank
    private String nome;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @Deprecated
    Estado() {
    }

    public Estado(final String nome, final Pais pais) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.pais = pais;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Pais getPais() {
        return pais;
    }

}
