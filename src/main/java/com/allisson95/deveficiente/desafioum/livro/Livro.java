package com.allisson95.deveficiente.desafioum.livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.allisson95.deveficiente.desafioum.autor.Autor;
import com.allisson95.deveficiente.desafioum.categoria.Categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    private UUID id;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String resumo;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String sumario;

    @NotNull
    @Min(20)
    private BigDecimal preco;

    @NotNull
    @Min(100)
    private Integer paginas;

    @NotBlank
    @ISBN
    private String isbn;

    @NotNull
    @Future
    private LocalDate dataPublicacao;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Deprecated
    Livro() {
    }

    public Livro(
            final String titulo,
            final String resumo,
            final String sumario,
            final BigDecimal preco,
            final Integer paginas,
            final String isbn,
            final LocalDate dataPublicacao,
            final Categoria categoria,
            final Autor autor) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.paginas = paginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.autor = autor;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getResumo() {
        return this.resumo;
    }

    public String getSumario() {
        return this.sumario;
    }

    public BigDecimal getPreco() {
        return this.preco;
    }

    public Integer getPaginas() {
        return this.paginas;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public LocalDate getDataPublicacao() {
        return this.dataPublicacao;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public Autor getAutor() {
        return this.autor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.titulo, this.isbn);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final Livro other = (Livro) obj;
        return Objects.equals(this.titulo, other.titulo) && Objects.equals(this.isbn, other.isbn);
    }

}
