package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.domain.Trainer;
import com.epam.gym.service.dto.Credentials;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTrainerDao implements TrainerDao {
    private static final Map<String, Trainer> trainers = new ConcurrentHashMap<>();
    private static final Map<String, String> passwords = new ConcurrentHashMap<>();

    @Override
    public Trainer save(Trainer t) {
        store.put(t.getUsername(), t);
        return t;
    }

    @Override
    public Trainer findByUsername(String username) {
        return trainers.get(username);
    }

    @Override
    public Trainer update(String username, String firstName, String lastName, String specialization) {
        Trainer t = trainers.get(username);
        if (t != null) {
            t.setFirstName(firstName);
            t.setLastName(lastName);
            t.setSpecialization(specialization);
        }
        return t;
    }

    @Override
    public List<String> findTrainees(String trainerUsername) {
        List<String> result = new ArrayList<>();
        InMemoryTraineeDao.traineeToTrainers.forEach((trainee, trainersSet) -> {
            if (trainersSet.contains(trainerUsername)) {
                result.add(trainee);
            }
        });
        return result;
    }
}