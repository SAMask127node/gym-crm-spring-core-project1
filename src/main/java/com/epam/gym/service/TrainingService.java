package com.epam.gym.service;

import com.epam.gym.domain.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    private final Map<Long, Training> trainingMap;
    private long nextId = 1L;

    public TrainingService(@Qualifier("trainingStorage") Map<Long, Training> trainingMap) {
        this.trainingMap = trainingMap;
    }

    @PostConstruct
    private void initNextId() {
        trainingMap.keySet()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    public Training create(String name, String traineeUsername, String trainerUsername) {
        logger.info("Creating Training {} (trainee={}, trainer={})", name, traineeUsername, trainerUsername);
        Training t = new Training(nextId++, name, traineeUsername, trainerUsername);
        trainingMap.put(t.id(), t);
        logger.debug("Training created: id={}, name={}", t.id(), t.name());
        return t;
    }

    public Optional<Training> find(Long id) {
        logger.debug("Finding Training ID={}", id);
        return Optional.ofNullable(trainingMap.get(id));
    }

    public Collection<Training> findAll() {
        logger.debug("Listing all trainings");
        return trainingMap.values();
    }
}
