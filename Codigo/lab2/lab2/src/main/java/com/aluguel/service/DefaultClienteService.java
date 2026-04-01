package com.aluguel.service;

import java.util.List;
import java.util.Optional;

import com.aluguel.model.ClienteEntity;
import com.aluguel.repository.ClienteRepository;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

@Singleton
public class DefaultClienteService implements ClienteService {
    private final ClienteRepository clienteRepository;

    public DefaultClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Iterable<ClienteEntity> list() {
        return clienteRepository.findAll();
    }

    public ClienteEntity save(ClienteEntity cliente) {
        if(cliente.getId() == null)
            return clienteRepository.save(cliente);
        else
            return clienteRepository.update(cliente);
    }

    public Optional<ClienteEntity> find(@NonNull String id) {
        return clienteRepository.findById(id);
    }

    public Iterable<ClienteEntity> findByNameInList(@NonNull List<String> names) {
        return clienteRepository.findByNameInList(names);
    }
    
}
