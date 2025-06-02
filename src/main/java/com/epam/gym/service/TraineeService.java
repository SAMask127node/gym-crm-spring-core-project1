package com.epam.gym.service;

import com.epam.gym.domain.Trainee;
import com.epam.gym.util.CredentialGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    private final Map<String, Trainee> traineeMap;
    private long nextId = 1L;

    public TraineeService(@Qualifier("traineeStorage") Map<String, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
    }

    @PostConstruct
    private void initNextId() {
        traineeMap.values()
                .stream()
                .mapToLong(Trainee::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    public Trainee create(String firstName, String lastName) {
        logger.info("Creating Trainee {} {}", firstName, lastName);
        String username = CredentialGenerator.uniqueUsername(firstName, lastName, traineeMap);
        String password = CredentialGenerator.randomPassword();
        Trainee t = new Trainee(nextId++, firstName, lastName, username, password);
        traineeMap.put(username, t);
        logger.debug("Trainee created: username={}, password={}", username, password);
        return t;
    }

    public Trainee update(String username, String newFirst, String newLast) {
        logger.info("Updating Trainee {}", username);
        Trainee old = Optional.ofNullable(traineeMap.get(username))
                .orElseThrow(() -> new IllegalArgumentException("No such Trainee: " + username));
        Trainee updated = new Trainee(old.id(), newFirst, newLast, old.username(), old.password());
        traineeMap.put(username, updated);
        logger.debug("Trainee updated: {}", username);
        return updated;
    }

    public void delete(String username) {
        logger.info("Deleting Trainee {}", username);
        if (!traineeMap.containsKey(username)) {
            throw new IllegalArgumentException("No such Trainee: " + username);
        }
        traineeMap.remove(username);
    }

    public Optional<Trainee> find(String username) {
        logger.debug("Finding Trainee {}", username);
        return Optional.ofNullable(traineeMap.get(username));
    }

    public Collection<Trainee> findAll() {
        logger.debug("Listing all trainees");
        return traineeMap.values();
    }
}
