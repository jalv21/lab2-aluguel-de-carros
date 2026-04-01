package com.aluguel.controller;

import java.util.List;
import java.util.Optional;

import com.aluguel.model.ClienteEntity;
import com.aluguel.service.ClienteService;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;

@Controller("/clientes")
@ExecuteOn(TaskExecutors.BLOCKING)
public class ClienteController {
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Get
    Iterable<ClienteEntity> list() {
        return clienteService.list();
    }

    @Post
    @Status(HttpStatus.CREATED)
    ClienteEntity save(ClienteEntity cliente) {
        return clienteService.save(cliente);
    }

    @Put
    ClienteEntity update(@NonNull @Valid ClienteEntity cliente) {
        return clienteService.save(cliente);
    }

    @Get("/{id}")
    Optional<ClienteEntity> find(@NonNull String id) {
        return clienteService.find(id);
    }

    @Get("/q")
    Iterable<ClienteEntity> query(@QueryValue @NonNull List<String> names) {
        return clienteService.findByNameInList(names);
    }
}