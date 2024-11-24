package com.allisson95.deveficiente.desafioum.livro;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

// 6
@RestController
@RequestMapping("/livros")
public class LivrosController {

    private final EntityManager entityManager;
    // 1
    private final LivroRepository livroRepository;

    public LivrosController(final EntityManager entityManager, final LivroRepository livroRepository) {
        this.entityManager = entityManager;
        this.livroRepository = livroRepository;
    }

    @PostMapping
    @Transactional
    // 2
    public LivroResponse criar(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        // 1
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

    @GetMapping("{id}")
    // 1
    public LivroDetalheResponse detalhar(@UUID(version = 4) @PathVariable String id) {
        // 1
        return this.livroRepository.findById(java.util.UUID.fromString(id))
                .map(LivroDetalheResponse::of)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

}
