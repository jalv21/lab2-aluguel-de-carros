package com.aluguel.controller;

import com.aluguel.model.Rendimento;
import com.aluguel.service.RendimentoService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Rendimento CRUD operations
 */
@Controller("/rendimentos")
public class RendimentoController {

    @Inject
    private RendimentoService rendimentoService;

    /**
     * Create a new income record
     * POST /rendimentos
     */
    @Post
    public HttpResponse<Rendimento> criar(@Body Rendimento rendimento) {
        try {
            Rendimento novoRendimento = rendimentoService.criar(rendimento);
            return HttpResponse.created(novoRendimento, URI.create("/rendimentos/" + novoRendimento.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Get an income record by ID
     * GET /rendimentos/{id}
     */
    @Get("/{id}")
    public HttpResponse<Rendimento> obter(Long id) {
        Optional<Rendimento> rendimento = rendimentoService.obter(id);
        return rendimento.map(HttpResponse::ok)
                         .orElse(HttpResponse.notFound());
    }

    /**
     * Get all income records
     * GET /rendimentos
     */
    @Get
    public HttpResponse<List<Rendimento>> listar() {
        List<Rendimento> rendimentos = rendimentoService.listar();
        return HttpResponse.ok(rendimentos);
    }

    /**
     * Update an income record
     * PUT /rendimentos/{id}
     */
    @Put("/{id}")
    public HttpResponse<Rendimento> atualizar(Long id, @Body Rendimento rendimento) {
        try {
            Optional<Rendimento> atualizado = rendimentoService.atualizar(id, rendimento);
            return atualizado.map(HttpResponse::ok)
                            .orElse(HttpResponse.notFound());
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Delete an income record
     * DELETE /rendimentos/{id}
     */
    @Delete("/{id}")
    public HttpResponse<Void> deletar(Long id) {
        if (rendimentoService.deletar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }
}
