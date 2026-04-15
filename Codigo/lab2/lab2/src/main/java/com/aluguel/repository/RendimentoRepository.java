package com.aluguel.repository;

import com.aluguel.model.Rendimento;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Rendimento CRUD operations
 */
public interface RendimentoRepository {

    Rendimento save(Rendimento rendimento);

    Optional<Rendimento> findById(Long id);

    List<Rendimento> findAll();

    Optional<Rendimento> update(Long id, Rendimento rendimento);

    boolean delete(Long id);

    boolean existsById(Long id);
}
