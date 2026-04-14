package com.aluguel.repository;

import com.aluguel.model.Automovel;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Automovel CRUD operations
 */
public interface AutomovelRepository {

    Automovel save(Automovel automovel);

    Optional<Automovel> findById(Long id);

    Optional<Automovel> findByPlaca(String placa);

    List<Automovel> findAll();

    Optional<Automovel> update(Long id, Automovel automovel);

    boolean delete(Long id);

    boolean existsById(Long id);
}
