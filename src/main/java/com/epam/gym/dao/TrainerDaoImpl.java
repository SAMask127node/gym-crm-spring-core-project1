package com.epam.gym.dao;

import com.epam.gym.domain.Trainer;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private final Map<String, Trainer> storage = new HashMap<>();

    @Override
    public Trainer save(Trainer trainer) {
        storage.put(trainer.username(), trainer);
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return Optional.ofNullable(storage.get(username));
    }

    @Override
    public Collection<Trainer> findAll() {
        return Collections.unmodifiableCollection(storage.values());
    }

    @Override
    public void deleteByUsername(String username) {
        storage.remove(username);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
