package com.epam.gym.service;

import com.epam.gym.domain.Training;
import com.epam.gym.domain.TrainingType;
import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    Training create(String name, String traineeUsername, String trainerUsername, LocalDate date, int duration, String typeCode);
    List<Training> findByTrainee(String traineeUsername);
    List<Training> findByTrainer(String trainerUsername);
    String activate(Long id);
    String deactivate(Long id);
    List<TrainingType> getTypes();
}