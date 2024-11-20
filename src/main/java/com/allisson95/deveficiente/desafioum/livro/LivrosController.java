package com.allisson95.deveficiente.desafioum.livro;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final LivroRepository livroRepository;

    public LivrosController(final EntityManager entityManager, final LivroRepository livroRepository) {
        this.entityManager = entityManager;
        this.livroRepository = livroRepository;
    }

    @PostMapping
    @Transactional
    public LivroResponse criar(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        final Livro livro = novoLivroRequest.toModel(this.entityManager);
        this.entityManager.persist(livro);
        return LivroResponse.of(livro);
    }

    @GetMapping
    public List<ListaLivrosResponse> listar() {
        return this.livroRepository.findAll()
                .stream()
                .map(ListaLivrosResponse::of)
                .toList();
    }

}
