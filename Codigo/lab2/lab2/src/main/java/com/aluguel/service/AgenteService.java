package com.aluguel.service;

import com.aluguel.model.Agente;
import com.aluguel.repository.AgenteRepository;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Agente CRUD operations
 */
@Prototype
public class AgenteService {

    @Inject
    protected AgenteRepository agenteRepository;

    public Agente criar(Agente agente) {
        if (agente.getCnpj() != null && agenteRepository.findByCnpj(agente.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("CNPJ already exists");
        }
        return agenteRepository.save(agente);
    }

    public Optional<Agente> obter(Long id) {
        return agenteRepository.findById(id);
    }

    public List<Agente> listar() {
        return agenteRepository.findAll();
    }

    public Optional<Agente> atualizar(Long id, Agente agente) {
        if (!agenteRepository.existsById(id)) {
            return Optional.empty();
        }
        Optional<Agente> existente = agenteRepository.findById(id);
        if (existente.isPresent() && agente.getCnpj() != null &&
            !agente.getCnpj().equals(existente.get().getCnpj())) {
            if (agenteRepository.findByCnpj(agente.getCnpj()).isPresent()) {
                throw new IllegalArgumentException("CNPJ already exists");
            }
        }
        return agenteRepository.update(id, agente);
    }

    public boolean deletar(Long id) {
        return agenteRepository.delete(id);
    }

    public Optional<Agente> obterPorCnpj(String cnpj) {
        return agenteRepository.findByCnpj(cnpj);
    }
}
