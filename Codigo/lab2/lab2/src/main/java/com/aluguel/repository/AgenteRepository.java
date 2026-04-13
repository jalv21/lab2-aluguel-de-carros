package com.aluguel.repository;

import com.aluguel.model.Agente;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Agente CRUD operations
 */
public interface AgenteRepository {

    Agente save(Agente agente);

    Optional<Agente> findById(Long id);

    Optional<Agente> findByCnpj(String cnpj);

    List<Agente> findAll();

    Optional<Agente> update(Long id, Agente agente);

    boolean delete(Long id);

    boolean existsById(Long id);
}
