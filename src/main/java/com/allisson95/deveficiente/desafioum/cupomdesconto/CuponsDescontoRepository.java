package com.allisson95.deveficiente.desafioum.cupomdesconto;

import java.util.Optional;
import java.util.UUID;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface CuponsDescontoRepository extends BaseJpaRepository<CupomDesconto, UUID> {

    Optional<CupomDesconto> findByCodigo(String codigoCupom);

}
