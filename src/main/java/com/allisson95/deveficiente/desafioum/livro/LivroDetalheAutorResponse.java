package com.allisson95.deveficiente.desafioum.livro;

import com.allisson95.deveficiente.desafioum.autor.Autor;

public record LivroDetalheAutorResponse(
        String id,
        String nome,
        String descricao) {

    public static LivroDetalheAutorResponse of(final Autor autor) {
        return new LivroDetalheAutorResponse(autor.getId().toString(), autor.getNome(), autor.getDescricao());
    }

}
