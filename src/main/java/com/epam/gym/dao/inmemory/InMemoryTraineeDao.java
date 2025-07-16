package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.domain.Trainee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTraineeDao implements TraineeDao {
    private final Map<Long, Trainee> byId = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return byId.values().stream()
                .filter(t -> t.getUser().getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(byId.values());
    }

    @Override
    public Trainee create(Trainee trainee) {
        long id = idGen.incrementAndGet();
        trainee.setId(id);
        byId.put(id, trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        byId.put(trainee.getId(), trainee);
        return trainee;
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
