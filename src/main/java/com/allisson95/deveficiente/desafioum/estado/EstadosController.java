package com.allisson95.deveficiente.desafioum.estado;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allisson95.deveficiente.desafioum.pais.PaisNaoEncontradoException;
import com.allisson95.deveficiente.desafioum.pais.PaisRepository;

import jakarta.validation.Valid;

// 7
@RestController
@RequestMapping("/paises/{paisId}/estados")
public class EstadosController {

    // 1
    private final EstadoRepository estadoRepository;
    // 1
    private final PaisRepository paisRepository;

    public EstadosController(final EstadoRepository estadoRepository, final PaisRepository paisRepository) {
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    // 2
    public EstadoResponse cadastrar(@PathVariable UUID paisId, @RequestBody @Valid NovoEstadoRequest request) {
        // 1
        if (!paisRepository.existsById(paisId)) {
            throw new PaisNaoEncontradoException(paisId.toString());
        }

        // 1
        final Estado novoEstado = request.toModel(paisRepository.getReferenceById(paisId));

        return EstadoResponse.of(this.estadoRepository.persist(novoEstado));
    }

}
