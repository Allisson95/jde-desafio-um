package com.allisson95.deveficiente.desafioum.livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.allisson95.deveficiente.desafioum.autor.Autor;
import com.allisson95.deveficiente.desafioum.categoria.Categoria;
import com.allisson95.deveficiente.desafioum.configuracoes.validation.ExistsById;
import com.allisson95.deveficiente.desafioum.configuracoes.validation.Unique;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NovoLivroRequest(
        @NotBlank @Unique(entity = Livro.class, field = "titulo") String titulo,
        @NotBlank @Size(max = 500) String resumo,
        @NotBlank String sumario,
        @NotNull @Min(20) BigDecimal preco,
        @NotNull @Min(100) Integer paginas,
        @NotBlank @ISBN @Unique(entity = Livro.class, field = "isbn") String isbn,
        @NotNull @Future LocalDate dataPublicacao,
        @NotNull @ExistsById(entity = Categoria.class) UUID categoriaId,
        @NotNull @ExistsById(entity = Autor.class) UUID autorId) {

    public Livro toModel(final EntityManager entityManager) {
        final Categoria categoria = entityManager.getReference(Categoria.class, this.categoriaId);
        final Autor autor = entityManager.getReference(Autor.class, this.autorId);

        return new Livro(
                this.titulo,
                this.resumo,
                this.sumario,
                this.preco,
                this.paginas,
                this.isbn,
                this.dataPublicacao,
                categoria,
                autor);
    }

}
