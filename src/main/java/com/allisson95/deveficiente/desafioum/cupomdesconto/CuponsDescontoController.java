package com.allisson95.deveficiente.desafioum.cupomdesconto;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cupons")
public class CuponsDescontoController {

    private final CuponsDescontoRepository cuponsDescontoRepository;

    public CuponsDescontoController(final CuponsDescontoRepository cuponsDescontoRepository) {
        this.cuponsDescontoRepository = cuponsDescontoRepository;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void criar(@RequestBody @Valid final NovoCupomRequest request) {
        this.cuponsDescontoRepository.persist(request.toModel());
    }

}
