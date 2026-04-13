package com.aluguel.repository;

import com.aluguel.model.Rendimento;
import io.micronaut.context.annotation.Prototype;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of RendimentoRepository
 */
@Prototype
public class RendimentoRepositoryImpl implements RendimentoRepository {

    private final Map<Long, Rendimento> rendimentos = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Rendimento save(Rendimento rendimento) {
        if (rendimento.getId() == null) {
            rendimento.setId(idCounter.getAndIncrement());
        }
        rendimentos.put(rendimento.getId(), rendimento);
        return rendimento;
    }

    @Override
    public Optional<Rendimento> findById(Long id) {
        return Optional.ofNullable(rendimentos.get(id));
    }

    @Override
    public List<Rendimento> findAll() {
        return new ArrayList<>(rendimentos.values());
    }

    @Override
    public Optional<Rendimento> update(Long id, Rendimento rendimento) {
        if (rendimentos.containsKey(id)) {
            rendimento.setId(id);
            rendimentos.put(id, rendimento);
            return Optional.of(rendimento);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return rendimentos.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return rendimentos.containsKey(id);
    }
}
