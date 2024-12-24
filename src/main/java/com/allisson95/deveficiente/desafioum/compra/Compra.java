package com.allisson95.deveficiente.desafioum.compra;

import java.util.UUID;
import java.util.function.Function;

import com.allisson95.deveficiente.desafioum.configuracoes.validation.Documento;
import com.allisson95.deveficiente.desafioum.estado.Estado;
import com.allisson95.deveficiente.desafioum.pais.Pais;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    private final UUID id;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String nome;

    @NotBlank
    private final String sobrenome;

    @NotBlank
    @Documento
    private final String documento;

    @NotBlank
    private final String cep;

    @NotBlank
    private final String endereco;

    @NotBlank
    private final String complemento;

    @NotBlank
    private final String cidade;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "pais_id")
    private final Pais pais;

    @ManyToOne(optional = true)
    @JoinColumn(name = "estado_id")
    private final Estado estado;

    @NotBlank
    private final String telefone;

    @Valid
    @NotNull
    @OneToOne(mappedBy = "compra", cascade = { CascadeType.PERSIST }, orphanRemoval = true)
    private final Pedido pedido;

    /**
     * @deprecated Para uso exclusivo do Hibernate
     */
    @Deprecated(since = "1.0")
    Compra() {
        this.id = null;
        this.email = null;
        this.nome = null;
        this.sobrenome = null;
        this.documento = null;
        this.cep = null;
        this.endereco = null;
        this.complemento = null;
        this.cidade = null;
        this.pais = null;
        this.estado = null;
        this.telefone = null;
        this.pedido = null;
    }

    public Compra(
            final String email,
            final String nome,
            final String sobrenome,
            final String documento,
            final String cep,
            final String endereco,
            final String complemento,
            final String cidade,
            final Pais pais,
            final Estado estado,
            final String telefone,
            final Function<Compra, Pedido> pedidoFactoryFn) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.cep = cep;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.estado = estado;
        this.telefone = telefone;
        this.pedido = pedidoFactoryFn.apply(this);
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public String getDocumento() {
        return this.documento;
    }

    public String getCep() {
        return this.cep;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Pais getPais() {
        return this.pais;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

}