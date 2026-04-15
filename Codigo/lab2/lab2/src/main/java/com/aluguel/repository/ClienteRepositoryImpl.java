package com.aluguel.repository;

import com.aluguel.model.Cliente;
import jakarta.inject.Singleton;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of ClienteRepository
 */
@Singleton
public class ClienteRepositoryImpl implements ClienteRepository {

    private final Map<Long, Cliente> clientes = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(idCounter.getAndIncrement());
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        return clientes.values().stream()
                .filter(c -> c.getCpf() != null && c.getCpf().equals(cpf))
                .findFirst();
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Optional<Cliente> update(Long id, Cliente cliente) {
        if (clientes.containsKey(id)) {
            cliente.setId(id);
            clientes.put(id, cliente);
            return Optional.of(cliente);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return clientes.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return clientes.containsKey(id);
    }
}
