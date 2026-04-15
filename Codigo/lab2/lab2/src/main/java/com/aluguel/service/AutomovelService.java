package com.aluguel.service;

import com.aluguel.model.Automovel;
import com.aluguel.repository.AutomovelRepository;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Automovel CRUD operations
 */
@Prototype
public class AutomovelService {

    @Inject
    protected AutomovelRepository automovelRepository;

    public Automovel criar(Automovel automovel) {
        if (automovel.getPlaca() != null && automovelRepository.findByPlaca(automovel.getPlaca()).isPresent()) {
            throw new IllegalArgumentException("Placa already exists");
        }
        return automovelRepository.save(automovel);
    }

    public Optional<Automovel> obter(Long id) {
        return automovelRepository.findById(id);
    }

    public List<Automovel> listar() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> atualizar(Long id, Automovel automovel) {
        if (!automovelRepository.existsById(id)) {
            return Optional.empty();
        }
        Optional<Automovel> existente = automovelRepository.findById(id);
        if (existente.isPresent() && automovel.getPlaca() != null &&
            !automovel.getPlaca().equals(existente.get().getPlaca())) {
            if (automovelRepository.findByPlaca(automovel.getPlaca()).isPresent()) {
                throw new IllegalArgumentException("Placa already exists");
            }
        }
        return automovelRepository.update(id, automovel);
    }

    public boolean deletar(Long id) {
        return automovelRepository.delete(id);
    }

    public Optional<Automovel> obterPorPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }
}
