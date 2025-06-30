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
    public Credentials create(String firstName, String lastName, String specialization) {
        String base = firstName + "." + lastName;
        String username = base;
        AtomicInteger suffix = new AtomicInteger(0);
        while (trainers.containsKey(username)) {
            username = base + suffix.incrementAndGet();
        }
        Trainer t = new Trainer();
        t.setUsername(username);
        t.setFirstName(firstName);
        t.setLastName(lastName);
        t.setSpecialization(specialization);
        trainers.put(username, t);
        passwords.put(username, UUID.randomUUID().toString());
        return new Credentials(username, passwords.get(username));
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