package com.allisson95.deveficiente.desafioum.autor;

import java.util.Optional;
import java.util.UUID;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface AutorRepository extends BaseJpaRepository<Autor, UUID> {

    Optional<Autor> findByEmail(String email);

}
