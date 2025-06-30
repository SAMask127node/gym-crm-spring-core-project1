package com.epam.gym.service;

import com.epam.gym.domain.Trainer;

import java.util.Collection;
import java.util.Optional;

/**
 * Service contract (business logic) for Trainer.
 */
public interface TrainerService {
    /**
     * Create a new Trainer with unique username and random password.
     */
    Trainer create(String firstName, String lastName);

    /**
     * Find by username.
     */
    Optional<Trainer> find(String username);

    /**
     * List all trainers.
     */
    Collection<Trainer> findAll();
}
