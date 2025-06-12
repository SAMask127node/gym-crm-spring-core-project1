package com.epam.gym.dao;

import com.epam.gym.domain.Trainee;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    private final Map<String, Trainee> storage = new HashMap<>();

    @Override
    public Trainee save(Trainee trainee) {
        storage.put(trainee.username(), trainee);
        return trainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return Optional.ofNullable(storage.get(username));
    }

    @Override
    public Collection<Trainee> findAll() {
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
