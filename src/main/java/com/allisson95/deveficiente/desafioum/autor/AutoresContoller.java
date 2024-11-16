package com.allisson95.deveficiente.desafioum.autor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

// 2
@RestController
@RequestMapping("/autores")
public class AutoresContoller {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping
    // 1
    public Autor cadastrar(@RequestBody @Valid final NovoAutorRequest request) {
        // 1
        Autor autor = request.toModel();
        entityManager.persist(autor);
        return autor;
    }

}
