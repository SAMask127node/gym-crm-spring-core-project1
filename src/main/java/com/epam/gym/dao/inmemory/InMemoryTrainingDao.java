package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.TrainingDao;
import com.epam.gym.domain.Training;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTrainingDao implements TrainingDao {
    private final Map<Long, Training> byId = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(byId.values());
    }

    @Override
    public Training create(Training training) {
        long id = idGen.incrementAndGet();
        training.setId(id);
        byId.put(id, training);
        return training;
    }

    @Override
    public Training update(Training training) {
        byId.put(training.getId(), training);
        return training;
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
