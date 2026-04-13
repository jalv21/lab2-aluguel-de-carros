package com.aluguel.repository;

import com.aluguel.model.Agente;
import jakarta.inject.Singleton;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of AgenteRepository
 */
@Singleton
public class AgenteRepositoryImpl implements AgenteRepository {

    private final Map<Long, Agente> agentes = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Agente save(Agente agente) {
        if (agente.getId() == null) {
            agente.setId(idCounter.getAndIncrement());
        }
        agentes.put(agente.getId(), agente);
        return agente;
    }

    @Override
    public Optional<Agente> findById(Long id) {
        return Optional.ofNullable(agentes.get(id));
    }

    @Override
    public Optional<Agente> findByCnpj(String cnpj) {
        return agentes.values().stream()
                .filter(a -> a.getCnpj() != null && a.getCnpj().equals(cnpj))
                .findFirst();
    }

    @Override
    public List<Agente> findAll() {
        return new ArrayList<>(agentes.values());
    }

    @Override
    public Optional<Agente> update(Long id, Agente agente) {
        if (agentes.containsKey(id)) {
            agente.setId(id);
            agentes.put(id, agente);
            return Optional.of(agente);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return agentes.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return agentes.containsKey(id);
    }
}
