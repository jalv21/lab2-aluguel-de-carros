package com.aluguel.repository;

import com.aluguel.model.Contrato;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Contrato CRUD operations
 */
public interface ContratoRepository {

    Contrato save(Contrato contrato);

    Optional<Contrato> findById(Long id);

    Optional<Contrato> findByNumero(Long numero);

    List<Contrato> findAll();

    Optional<Contrato> update(Long id, Contrato contrato);

    boolean delete(Long id);

    boolean existsById(Long id);
}
