package com.epam.gym.dao;

import com.epam.gym.domain.Trainee;

import java.util.Collection;
import java.util.Optional;

public interface TraineeDao {
    Trainee save(Trainee trainee);
    Optional<Trainee> findByUsername(String username);
    Collection<Trainee> findAll();
    void deleteByUsername(String username);
    void deleteAll();
}