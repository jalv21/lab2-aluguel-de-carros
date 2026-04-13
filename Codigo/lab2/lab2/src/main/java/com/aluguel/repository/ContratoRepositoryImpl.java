package com.aluguel.repository;

import com.aluguel.model.Contrato;
import io.micronaut.context.annotation.Prototype;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of ContratoRepository
 */
@Prototype
public class ContratoRepositoryImpl implements ContratoRepository {

    private final Map<Long, Contrato> contratos = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Contrato save(Contrato contrato) {
        if (contrato.getId() == null) {
            contrato.setId(idCounter.getAndIncrement());
        }
        contratos.put(contrato.getId(), contrato);
        return contrato;
    }

    @Override
    public Optional<Contrato> findById(Long id) {
        return Optional.ofNullable(contratos.get(id));
    }

    @Override
    public Optional<Contrato> findByNumero(Long numero) {
        return contratos.values().stream()
                .filter(c -> c.getNumero() != null && c.getNumero().equals(numero))
                .findFirst();
    }

    @Override
    public List<Contrato> findAll() {
        return new ArrayList<>(contratos.values());
    }

    @Override
    public Optional<Contrato> update(Long id, Contrato contrato) {
        if (contratos.containsKey(id)) {
            contrato.setId(id);
            contratos.put(id, contrato);
            return Optional.of(contrato);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return contratos.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return contratos.containsKey(id);
    }
}
