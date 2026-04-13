package com.aluguel.repository;

import com.aluguel.model.Automovel;
import io.micronaut.context.annotation.Prototype;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of AutomovelRepository
 */
@Prototype
public class AutomovelRepositoryImpl implements AutomovelRepository {

    private final Map<Long, Automovel> automoveis = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Automovel save(Automovel automovel) {
        if (automovel.getId() == null) {
            automovel.setId(idCounter.getAndIncrement());
        }
        automoveis.put(automovel.getId(), automovel);
        return automovel;
    }

    @Override
    public Optional<Automovel> findById(Long id) {
        return Optional.ofNullable(automoveis.get(id));
    }

    @Override
    public Optional<Automovel> findByPlaca(String placa) {
        return automoveis.values().stream()
                .filter(a -> a.getPlaca() != null && a.getPlaca().equals(placa))
                .findFirst();
    }

    @Override
    public List<Automovel> findAll() {
        return new ArrayList<>(automoveis.values());
    }

    @Override
    public Optional<Automovel> update(Long id, Automovel automovel) {
        if (automoveis.containsKey(id)) {
            automovel.setId(id);
            automoveis.put(id, automovel);
            return Optional.of(automovel);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return automoveis.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return automoveis.containsKey(id);
    }
}
