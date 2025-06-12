package com.epam.gym.service;

import com.epam.gym.domain.Trainee;

import java.util.Collection;
import java.util.Optional;

/**
 * Service contract (business logic) for Trainee.
 */
public interface TraineeService {
    /**
     * Create a new Trainee with unique username and random password.
     */
    Trainee create(String firstName, String lastName);

    /**
     * Update an existing trainee’s first/last name (username and password unchanged).
     */
    Trainee update(String username, String newFirst, String newLast);

    /**
     * Delete by username.
     */
    void delete(String username);

    /**
     * Find by username.
     */
    Optional<Trainee> find(String username);

    /**
     * List all trainees.
     */
    Collection<Trainee> findAll();
}