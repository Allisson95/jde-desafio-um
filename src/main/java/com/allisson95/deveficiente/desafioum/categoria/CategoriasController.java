package com.allisson95.deveficiente.desafioum.categoria;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

// 4
@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    // 1
    private final CategoriaRepository categoriaRepository;

    public CategoriasController(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    @PostMapping
    // 1
    public CategoriaResponse cadastrar(@RequestBody @Valid final NovaCategoriaRequest request) {
        // 1
        this.categoriaRepository.findByNome(request.nome()).ifPresent(categoria -> {
            throw new CategoriaExistenteException(categoria.getNome());
        });

        // 1
        final Categoria categoria = request.toModel();

        return CategoriaResponse.of(this.categoriaRepository.persist(categoria));
    }

}
