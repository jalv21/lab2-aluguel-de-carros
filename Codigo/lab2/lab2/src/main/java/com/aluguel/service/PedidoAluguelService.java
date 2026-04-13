package com.aluguel.service;

import com.aluguel.model.PedidoAluguel;
import com.aluguel.repository.PedidoAluguelRepository;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for PedidoAluguel CRUD operations
 */
@Prototype
public class PedidoAluguelService {

    @Inject
    protected PedidoAluguelRepository pedidoRepository;

    public PedidoAluguel criar(PedidoAluguel pedido) {
        if (pedido.getClienteId() == null || pedido.getAutomovelId() == null) {
            throw new IllegalArgumentException("Cliente and Automovel IDs are required");
        }
        return pedidoRepository.save(pedido);
    }

    public Optional<PedidoAluguel> obter(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<PedidoAluguel> listar() {
        return pedidoRepository.findAll();
    }

    public List<PedidoAluguel> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public Optional<PedidoAluguel> atualizar(Long id, PedidoAluguel pedido) {
        if (!pedidoRepository.existsById(id)) {
            return Optional.empty();
        }
        return pedidoRepository.update(id, pedido);
    }

    public boolean deletar(Long id) {
        return pedidoRepository.delete(id);
    }

    public boolean aprovar(Long id) {
        Optional<PedidoAluguel> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            PedidoAluguel p = pedido.get();
            p.setStatus("APROVADO");
            pedidoRepository.update(id, p);
            return true;
        }
        return false;
    }

    public boolean rejeitar(Long id) {
        Optional<PedidoAluguel> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            PedidoAluguel p = pedido.get();
            p.setStatus("REJEITADO");
            pedidoRepository.update(id, p);
            return true;
        }
        return false;
    }

    public boolean cancelar(Long id) {
        Optional<PedidoAluguel> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            PedidoAluguel p = pedido.get();
            p.setStatus("CANCELADO");
            pedidoRepository.update(id, p);
            return true;
        }
        return false;
    }
}
