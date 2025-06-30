package com.epam.gym.dao;

import com.epam.gym.domain.Training;

import java.util.Collection;
import java.util.Optional;

public interface TrainingDao {
    Training save(Training training);
    Optional<Training> findById(Long id);
    Collection<Training> findAll();
    void deleteById(Long id);
    void deleteAll();
}