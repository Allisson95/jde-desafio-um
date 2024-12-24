package com.allisson95.deveficiente.desafioum.compra;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/compras")
public class ComprasController {

    private final NovaCompraRequestValidator validator;
    private final ComprasRepository comprasRepository;
    private final EntityManager entityManager;

    public ComprasController(
            final NovaCompraRequestValidator validator,
            final ComprasRepository comprasRepository,
            final EntityManager entityManager) {
        this.validator = validator;
        this.comprasRepository = comprasRepository;
        this.entityManager = entityManager;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.addValidators(this.validator);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void comprar(@RequestBody @Valid final NovaCompraRequest request) {
        this.comprasRepository.persist(request.toModel(this.entityManager));
    }

}
