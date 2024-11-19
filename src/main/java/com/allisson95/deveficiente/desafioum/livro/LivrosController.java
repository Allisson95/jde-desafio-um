package com.allisson95.deveficiente.desafioum.livro;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivrosController {

    private final EntityManager entityManager;

    public LivrosController(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public Livro criar(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        final Livro livro = novoLivroRequest.toModel(this.entityManager);
        this.entityManager.persist(livro);
        return livro;
    }

}
