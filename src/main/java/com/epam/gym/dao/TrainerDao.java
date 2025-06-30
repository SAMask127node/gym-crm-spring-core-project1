package com.epam.gym.dao;

import com.epam.gym.domain.Trainer;

import java.util.Collection;
import java.util.Optional;

public interface TrainerDao {
    Trainer save(Trainer trainer);
    Optional<Trainer> findByUsername(String username);
    Collection<Trainer> findAll();
    void deleteByUsername(String username);
    void deleteAll();
}