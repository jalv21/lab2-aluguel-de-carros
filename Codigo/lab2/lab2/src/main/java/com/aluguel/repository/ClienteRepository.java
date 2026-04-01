package com.aluguel.repository;

import java.util.List;

import com.aluguel.model.ClienteEntity;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

@MongoRepository
public interface ClienteRepository extends CrudRepository<ClienteEntity, String> {
    @NonNull
    Iterable<ClienteEntity> findByNameInList(@NonNull List<String> names);
}
