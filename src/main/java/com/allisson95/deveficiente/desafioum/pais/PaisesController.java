package com.allisson95.deveficiente.desafioum.pais;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

// 4
@RestController
@RequestMapping("/paises")
public class PaisesController {

    // 1
    private final PaisRepository paisRepository;

    public PaisesController(final PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    // 2
    public PaisResponse cadastrar(@RequestBody @Valid final NovoPaisRequest request) {
        // 1
        final Pais novoPais = request.toModel();
        return PaisResponse.of(this.paisRepository.persist(novoPais));
    }

}
