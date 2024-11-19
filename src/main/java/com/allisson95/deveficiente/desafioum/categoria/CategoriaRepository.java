package com.allisson95.deveficiente.desafioum.categoria;

import java.util.Optional;
import java.util.UUID;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface CategoriaRepository extends BaseJpaRepository<Categoria, UUID> {

    Optional<Categoria> findByNome(String nome);

}
