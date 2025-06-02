package com.epam.gym.facade;

import com.epam.gym.domain.Trainee;
import com.epam.gym.domain.Trainer;
import com.epam.gym.domain.Training;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class GymFacade {
    private static final Logger logger = LoggerFactory.getLogger(GymFacade.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService
    ) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee addTrainee(String first, String last) {
        return traineeService.create(first, last);
    }

    public Trainer addTrainer(String first, String last) {
        return trainerService.create(first, last);
    }

    public Training scheduleTraining(String name, String traineeUsername, String trainerUsername) {
        Optional<Trainee> t1 = traineeService.find(traineeUsername);
        Optional<Trainer> t2 = trainerService.find(trainerUsername);
        if (t1.isEmpty() || t2.isEmpty()) {
            throw new IllegalArgumentException("Trainee or Trainer does not exist.");
        }
        return trainingService.create(name, traineeUsername, trainerUsername);
    }

    public Collection<Trainee> listTrainees() {
        return traineeService.findAll();
    }

    public Collection<Trainer> listTrainers() {
        return trainerService.findAll();
    }

    public Collection<Training> listTrainings() {
        return trainingService.findAll();
    }
}
