package com.epam.gym.dao;

import com.epam.gym.domain.Training;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private final Map<Long, Training> storage = new HashMap<>();

    @Override
    public Training save(Training training) {
        storage.put(training.id(), training);
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Collection<Training> findAll() {
        return Collections.unmodifiableCollection(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
