package com.aluguel.controller;

import com.aluguel.model.Agente;
import com.aluguel.service.AgenteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Agente CRUD operations
 */
@Controller("/agentes")
public class AgenteController {

    @Inject
    private AgenteService agenteService;

    /**
     * Create a new agent
     * POST /agentes
     */
    @Post
    public HttpResponse<Agente> criar(@Body Agente agente) {
        try {
            Agente novoAgente = agenteService.criar(agente);
            return HttpResponse.created(novoAgente, URI.create("/agentes/" + novoAgente.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Get an agent by ID
     * GET /agentes/{id}
     */
    @Get("/{id}")
    public HttpResponse<Agente> obter(Long id) {
        Optional<Agente> agente = agenteService.obter(id);
        return agente.map(HttpResponse::ok)
                     .orElse(HttpResponse.notFound());
    }

    /**
     * Get all agents
     * GET /agentes
     */
    @Get
    public HttpResponse<List<Agente>> listar() {
        List<Agente> agentes = agenteService.listar();
        return HttpResponse.ok(agentes);
    }

    /**
     * Update an agent
     * PUT /agentes/{id}
     */
    @Put("/{id}")
    public HttpResponse<Agente> atualizar(Long id, @Body Agente agente) {
        try {
            Optional<Agente> atualizado = agenteService.atualizar(id, agente);
            return atualizado.map(HttpResponse::ok)
                            .orElse(HttpResponse.notFound());
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Delete an agent
     * DELETE /agentes/{id}
     */
    @Delete("/{id}")
    public HttpResponse<Void> deletar(Long id) {
        if (agenteService.deletar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    /**
     * Get an agent by CNPJ
     * GET /agentes/cnpj/{cnpj}
     */
    @Get("/cnpj/{cnpj}")
    public HttpResponse<Agente> obterPorCnpj(String cnpj) {
        Optional<Agente> agente = agenteService.obter(1L); // Placeholder
        // TODO: Implement findByCnpj in AgenteService
        return agente.map(HttpResponse::ok)
                     .orElse(HttpResponse.notFound());
    }
}
