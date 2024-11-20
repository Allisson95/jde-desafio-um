package com.allisson95.deveficiente.desafioum.livro;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface LivroRepository extends BaseJpaRepository<Livro, UUID> {

    @Query("FROM Livro")
    List<Livro> findAll();

}
