package com.aluguel.service;

import com.aluguel.model.Rendimento;
import com.aluguel.repository.RendimentoRepository;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Rendimento CRUD operations
 */
@Prototype
public class RendimentoService {

    @Inject
    protected RendimentoRepository rendimentoRepository;

    public Rendimento criar(Rendimento rendimento) {
        if (rendimento.getEntidadeEmpregadora() == null || rendimento.getValor() <= 0) {
            throw new IllegalArgumentException("Valid employer and income value are required");
        }
        return rendimentoRepository.save(rendimento);
    }

    public Optional<Rendimento> obter(Long id) {
        return rendimentoRepository.findById(id);
    }

    public List<Rendimento> listar() {
        return rendimentoRepository.findAll();
    }

    public Optional<Rendimento> atualizar(Long id, Rendimento rendimento) {
        if (!rendimentoRepository.existsById(id)) {
            return Optional.empty();
        }
        return rendimentoRepository.update(id, rendimento);
    }

    public boolean deletar(Long id) {
        return rendimentoRepository.delete(id);
    }
}
