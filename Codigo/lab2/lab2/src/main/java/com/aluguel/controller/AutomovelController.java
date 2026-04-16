package com.aluguel.controller;

import com.aluguel.model.Automovel;
import com.aluguel.service.AutomovelService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Automovel CRUD operations
 */
@Controller("/automoveis")
public class AutomovelController {

    @Inject
    private AutomovelService automovelService;

    /**
     * Create a new vehicle
     * POST /automoveis
     */
    @Post
    public HttpResponse<Automovel> criar(@Body Automovel automovel) {
        try {
            Automovel novoAutomovel = automovelService.criar(automovel);
            return HttpResponse.created(novoAutomovel, URI.create("/automoveis/" + novoAutomovel.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Get a vehicle by ID
     * GET /automoveis/{id}
     */
    @Get("/{id}")
    public HttpResponse<Automovel> obter(Long id) {
        Optional<Automovel> automovel = automovelService.obter(id);
        return automovel.map(HttpResponse::ok)
                        .orElse(HttpResponse.notFound());
    }

    /**
     * Get all vehicles
     * GET /automoveis
     */
    @Get
    public HttpResponse<List<Automovel>> listar() {
        List<Automovel> automoveis = automovelService.listar();
        return HttpResponse.ok(automoveis);
    }

    /**
     * Update a vehicle
     * PUT /automoveis/{id}
     */
    @Put("/{id}")
    public HttpResponse<Automovel> atualizar(Long id, @Body Automovel automovel) {
        try {
            Optional<Automovel> atualizado = automovelService.atualizar(id, automovel);
            return atualizado.map(HttpResponse::ok)
                            .orElse(HttpResponse.notFound());
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Delete a vehicle
     * DELETE /automoveis/{id}
     */
    @Delete("/{id}")
    public HttpResponse<Void> deletar(Long id) {
        if (automovelService.deletar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    /**
     * Get a vehicle by license plate
     * GET /automoveis/placa/{placa}
     */
    @Get("/placa/{placa}")
    public HttpResponse<Automovel> obterPorPlaca(String placa) {
        Optional<Automovel> automovel = automovelService.obter(1L); // Placeholder
        // TODO: Implement findByPlaca in AutomovelService
        return automovel.map(HttpResponse::ok)
                        .orElse(HttpResponse.notFound());
    }
}
