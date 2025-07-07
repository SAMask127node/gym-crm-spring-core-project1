package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.domain.Trainee;
import com.epam.gym.service.dto.Credentials;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTraineeDao implements TraineeDao {
    private static final Map<String, Trainee> trainees = new ConcurrentHashMap<>();
    private static final Map<String, Set<String>> traineeToTrainers = new ConcurrentHashMap<>();
    private static final Map<String, String> passwords = new ConcurrentHashMap<>();

    public Trainee save(Trainee t) {
        storage.put(t.getUsername(), t);
        return t;
    }

    @Override
    public void changeLogin(String oldUsername, String newUsername) {
        Trainee t = trainees.remove(oldUsername);
        String pwd = passwords.remove(oldUsername);
        if (t != null && pwd != null) {
            t.setUsername(newUsername);
            trainees.put(newUsername, t);
            passwords.put(newUsername, pwd);
            // transfer assignments
            Set<String> trainers = traineeToTrainers.remove(oldUsername);
            if (trainers != null) {
                traineeToTrainers.put(newUsername, trainers);
            }
        }
    }

    @Override
    public Trainee findByUsername(String username) {
        return trainees.get(username);
    }

    @Override
    public Trainee update(String username, String firstName, String lastName, LocalDate dateOfBirth, String address) {
        Trainee t = trainees.get(username);
        if (t != null) {
            t.setFirstName(firstName);
            t.setLastName(lastName);
            t.setDateOfBirth(dateOfBirth);
            t.setAddress(address);
        }
        return t;
    }

    @Override
    public void deleteByUsername(String username) {
        trainees.remove(username);
        passwords.remove(username);
        traineeToTrainers.remove(username);
    }

    @Override
    public void assignTrainer(String traineeUsername, String trainerUsername) {
        traineeToTrainers
                .computeIfAbsent(traineeUsername, k -> ConcurrentHashMap.newKeySet())
                .add(trainerUsername);
    }

    @Override
    public List<String> findTrainers(String traineeUsername) {
        Set<String> set = traineeToTrainers.get(traineeUsername);
        return set == null ? Collections.emptyList() : new ArrayList<>(set);
    }
}