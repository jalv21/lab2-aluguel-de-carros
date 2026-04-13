package com.aluguel.service;

import com.aluguel.model.Contrato;
import com.aluguel.repository.ContratoRepository;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Contrato CRUD operations
 */
@Prototype
public class ContratoService {

    @Inject
    protected ContratoRepository contratoRepository;

    public Contrato criar(Contrato contrato) {
        if (contrato.getNumero() != null && contratoRepository.findByNumero(contrato.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Numero already exists");
        }
        return contratoRepository.save(contrato);
    }

    public Optional<Contrato> obter(Long id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> listar() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> atualizar(Long id, Contrato contrato) {
        if (!contratoRepository.existsById(id)) {
            return Optional.empty();
        }
        return contratoRepository.update(id, contrato);
    }

    public boolean deletar(Long id) {
        return contratoRepository.delete(id);
    }

    public boolean assinar(Long id) {
        Optional<Contrato> contrato = contratoRepository.findById(id);
        if (contrato.isPresent()) {
            Contrato c = contrato.get();
            c.setAssinado(true);
            contratoRepository.update(id, c);
            return true;
        }
        return false;
    }
}
