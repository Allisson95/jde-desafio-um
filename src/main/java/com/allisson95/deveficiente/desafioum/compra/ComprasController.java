package com.allisson95.deveficiente.desafioum.compra;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allisson95.deveficiente.desafioum.cupomdesconto.CuponsDescontoRepository;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/compras")
public class ComprasController {

    private final NovaCompraRequestValidator validator;
    private final ComprasRepository comprasRepository;
    private final CuponsDescontoRepository cuponsDescontoRepository;
    private final EntityManager entityManager;

    public ComprasController(
            final NovaCompraRequestValidator validator,
            final ComprasRepository comprasRepository,
            final CuponsDescontoRepository cuponsDescontoRepository,
            final EntityManager entityManager) {
        this.validator = validator;
        this.comprasRepository = comprasRepository;
        this.cuponsDescontoRepository = cuponsDescontoRepository;
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
        this.comprasRepository.persist(request.toModel(this.entityManager, this.cuponsDescontoRepository));
    }

    @GetMapping("{compraId}")
    public CompraResponse buscarCompra(@PathVariable final String compraId) {
        return this.comprasRepository.findById(UUID.fromString(compraId))
                .map(CompraResponse::new)
                .orElseThrow(() -> new CompraNaoEncontradaException(compraId));
    }

}
