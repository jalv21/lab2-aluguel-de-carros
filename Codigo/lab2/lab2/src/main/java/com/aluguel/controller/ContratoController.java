package com.aluguel.controller;

import com.aluguel.model.Contrato;
import com.aluguel.service.ContratoService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Contrato CRUD operations
 */
@Controller("/contratos")
public class ContratoController {

    @Inject
    private ContratoService contratoService;

    /**
     * Create a new contract
     * POST /contratos
     */
    @Post
    public HttpResponse<Contrato> criar(@Body Contrato contrato) {
        try {
            Contrato novoContrato = contratoService.criar(contrato);
            return HttpResponse.created(novoContrato, URI.create("/contratos/" + novoContrato.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Get a contract by ID
     * GET /contratos/{id}
     */
    @Get("/{id}")
    public HttpResponse<Contrato> obter(Long id) {
        Optional<Contrato> contrato = contratoService.obter(id);
        return contrato.map(HttpResponse::ok)
                       .orElse(HttpResponse.notFound());
    }

    /**
     * Get all contracts
     * GET /contratos
     */
    @Get
    public HttpResponse<List<Contrato>> listar() {
        List<Contrato> contratos = contratoService.listar();
        return HttpResponse.ok(contratos);
    }

    /**
     * Update a contract
     * PUT /contratos/{id}
     */
    @Put("/{id}")
    public HttpResponse<Contrato> atualizar(Long id, @Body Contrato contrato) {
        try {
            Optional<Contrato> atualizado = contratoService.atualizar(id, contrato);
            return atualizado.map(HttpResponse::ok)
                            .orElse(HttpResponse.notFound());
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Delete a contract
     * DELETE /contratos/{id}
     */
    @Delete("/{id}")
    public HttpResponse<Void> deletar(Long id) {
        if (contratoService.deletar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    /**
     * Get a contract by number
     * GET /contratos/numero/{numero}
     */
    @Get("/numero/{numero}")
    public HttpResponse<Contrato> obterPorNumero(String numero) {
        Optional<Contrato> contrato = contratoService.obter(1L); // Placeholder
        // TODO: Implement findByNumero in ContratoService
        return contrato.map(HttpResponse::ok)
                       .orElse(HttpResponse.notFound());
    }

    /**
     * Sign a contract
     * POST /contratos/{id}/assinar
     */
    @Post("/{id}/assinar")
    public HttpResponse<Void> assinar(Long id) {
        if (contratoService.assinar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }
}
