package com.aluguel.repository;

import com.aluguel.model.PedidoAluguel;
import io.micronaut.context.annotation.Prototype;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PedidoAluguelRepository
 */
@Prototype
public class PedidoAluguelRepositoryImpl implements PedidoAluguelRepository {

    private final Map<Long, PedidoAluguel> pedidos = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public PedidoAluguel save(PedidoAluguel pedido) {
        if (pedido.getId() == null) {
            pedido.setId(idCounter.getAndIncrement());
        }
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public Optional<PedidoAluguel> findById(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public List<PedidoAluguel> findByClienteId(Long clienteId) {
        return pedidos.values().stream()
                .filter(p -> p.getClienteId() != null && p.getClienteId().equals(clienteId))
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoAluguel> findAll() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public Optional<PedidoAluguel> update(Long id, PedidoAluguel pedido) {
        if (pedidos.containsKey(id)) {
            pedido.setId(id);
            pedidos.put(id, pedido);
            return Optional.of(pedido);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return pedidos.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return pedidos.containsKey(id);
    }
}
