package com.epam.gym.service;

import com.epam.gym.domain.Trainer;
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
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);

    private final Map<String, Trainer> trainerMap;
    private long nextId = 1L;

    public TrainerService(@Qualifier("trainerStorage") Map<String, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
    }

    @PostConstruct
    private void initNextId() {
        trainerMap.values()
                .stream()
                .mapToLong(Trainer::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    public Trainer create(String firstName, String lastName) {
        logger.info("Creating Trainer {} {}", firstName, lastName);
        String username = CredentialGenerator.uniqueUsername(firstName, lastName, trainerMap);
        String password = CredentialGenerator.randomPassword();
        Trainer t = new Trainer(nextId++, firstName, lastName, username, password);
        trainerMap.put(username, t);
        logger.debug("Trainer created: username={}, password={}", username, password);
        return t;
    }

    public Optional<Trainer> find(String username) {
        logger.debug("Finding Trainer {}", username);
        return Optional.ofNullable(trainerMap.get(username));
    }

    public Collection<Trainer> findAll() {
        logger.debug("Listing all trainers");
        return trainerMap.values();
    }
}
