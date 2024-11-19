package com.allisson95.deveficiente.desafioum.livro;

import java.util.UUID;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface LivroRepository extends BaseJpaRepository<Livro, UUID> {

}
