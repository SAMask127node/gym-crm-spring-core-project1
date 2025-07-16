package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.domain.Trainer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTrainerDao implements TrainerDao {
    private final Map<Long, Trainer> byId = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return byId.values().stream()
                .filter(t -> t.getUser().getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(byId.values());
    }

    @Override
    public Trainer create(Trainer trainer) {
        long id = idGen.incrementAndGet();
        trainer.setId(id);
        byId.put(id, trainer);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        byId.put(trainer.getId(), trainer);
        return trainer;
    }

    @Override
    public void deleteById(Long id) {
        byId.remove(id);
    }

    @Override
    public void deleteAll() {
        byId.clear();
        idGen.set(0);
    }
}
