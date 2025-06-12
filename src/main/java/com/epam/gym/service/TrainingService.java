package com.epam.gym.service;

import com.epam.gym.domain.Training;

import java.util.Collection;
import java.util.Optional;

/**
 * Service contract (business logic) for Training.
 */
public interface TrainingService {
    /**
     * Create a new Training (auto‐id) between a given traineeUsername and trainerUsername.
     */
    Training create(String name, String traineeUsername, String trainerUsername);

    /**
     * Find by ID.
     */
    Optional<Training> find(Long id);

    /**
     * List all trainings.
     */
    Collection<Training> findAll();
}
