package com.aluguel.service;

import java.util.List;
import java.util.Optional;

import com.aluguel.model.ClienteEntity;

import io.micronaut.core.annotation.NonNull;

public interface ClienteService {
    Iterable<ClienteEntity> list();
    ClienteEntity save(ClienteEntity cliente);
    Optional<ClienteEntity> find(@NonNull String id);
    Iterable<ClienteEntity> findByNameInList(@NonNull List<String> names);
}
