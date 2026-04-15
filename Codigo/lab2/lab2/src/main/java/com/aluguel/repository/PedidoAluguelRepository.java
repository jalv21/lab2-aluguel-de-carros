package com.aluguel.repository;

import com.aluguel.model.PedidoAluguel;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for PedidoAluguel CRUD operations
 */
public interface PedidoAluguelRepository {

    PedidoAluguel save(PedidoAluguel pedido);

    Optional<PedidoAluguel> findById(Long id);

    List<PedidoAluguel> findByClienteId(Long clienteId);

    List<PedidoAluguel> findAll();

    Optional<PedidoAluguel> update(Long id, PedidoAluguel pedido);

    boolean delete(Long id);

    boolean existsById(Long id);
}
