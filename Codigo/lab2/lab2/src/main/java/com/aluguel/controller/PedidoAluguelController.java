package com.aluguel.controller;

import com.aluguel.model.PedidoAluguel;
import com.aluguel.service.PedidoAluguelService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for PedidoAluguel CRUD operations
 */
@Controller("/pedidos-aluguel")
public class PedidoAluguelController {

    @Inject
    private PedidoAluguelService pedidoService;

    /**
     * Create a new rental request
     * POST /pedidos-aluguel
     */
    @Post
    public HttpResponse<PedidoAluguel> criar(@Body PedidoAluguel pedido) {
        try {
            PedidoAluguel novoPedido = pedidoService.criar(pedido);
            return HttpResponse.created(novoPedido, URI.create("/pedidos-aluguel/" + novoPedido.getId()));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Get a rental request by ID
     * GET /pedidos-aluguel/{id}
     */
    @Get("/{id}")
    public HttpResponse<PedidoAluguel> obter(Long id) {
        Optional<PedidoAluguel> pedido = pedidoService.obter(id);
        return pedido.map(HttpResponse::ok)
                     .orElse(HttpResponse.notFound());
    }

    /**
     * Get all rental requests
     * GET /pedidos-aluguel
     */
    @Get
    public HttpResponse<List<PedidoAluguel>> listar() {
        List<PedidoAluguel> pedidos = pedidoService.listar();
        return HttpResponse.ok(pedidos);
    }

    /**
     * Get rental requests by client
     * GET /pedidos-aluguel/cliente/{clienteId}
     */
    @Get("/cliente/{clienteId}")
    public HttpResponse<List<PedidoAluguel>> listarPorCliente(Long clienteId) {
        List<PedidoAluguel> pedidos = pedidoService.listarPorCliente(clienteId);
        return HttpResponse.ok(pedidos);
    }

    /**
     * Update a rental request
     * PUT /pedidos-aluguel/{id}
     */
    @Put("/{id}")
    public HttpResponse<PedidoAluguel> atualizar(Long id, @Body PedidoAluguel pedido) {
        try {
            Optional<PedidoAluguel> atualizado = pedidoService.atualizar(id, pedido);
            return atualizado.map(HttpResponse::ok)
                            .orElse(HttpResponse.notFound());
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    /**
     * Delete a rental request
     * DELETE /pedidos-aluguel/{id}
     */
    @Delete("/{id}")
    public HttpResponse<Void> deletar(Long id) {
        if (pedidoService.deletar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    /**
     * Approve a rental request
     * POST /pedidos-aluguel/{id}/aprovar
     */
    @Post("/{id}/aprovar")
    public HttpResponse<Void> aprovar(Long id) {
        if (pedidoService.aprovar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    /**
     * Reject a rental request
     * POST /pedidos-aluguel/{id}/rejeitar
     */
    @Post("/{id}/rejeitar")
    public HttpResponse<Void> rejeitar(Long id) {
        if (pedidoService.rejeitar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }

    /**
     * Cancel a rental request
     * POST /pedidos-aluguel/{id}/cancelar
     */
    @Post("/{id}/cancelar")
    public HttpResponse<Void> cancelar(Long id) {
        if (pedidoService.cancelar(id)) {
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }
}
