package com.allisson95.deveficiente.desafioum.autor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

// 2
@RestController
@RequestMapping("/autores")
public class AutoresContoller {

    private final AutorRepository autorRepository;

    public AutoresContoller(final AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Transactional
    @PostMapping
    // 1
    public Autor cadastrar(@RequestBody @Valid final NovoAutorRequest request) {
        // 1
        autorRepository.findByEmail(request.email()).ifPresent(autor -> {
            throw new AutorExistenteException(autor.getEmail());
        });

        // 1
        Autor autor = request.toModel();
        autorRepository.persist(autor);
        return autor;
    }

}
