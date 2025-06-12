package com.epam.gym.facade;

import com.epam.gym.domain.Trainee;
import com.epam.gym.domain.Trainer;
import com.epam.gym.domain.Training;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import org.springframework.stereotype.Component;

@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    // Delegates to TraineeService
    public Trainee createTrainee(String firstName, String lastName) {
        return traineeService.create(firstName, lastName);
    }

    public void deleteTrainee(String username) {
        traineeService.delete(username);
    }

    public Trainee findTrainee(String username) {
        return traineeService.find(username).orElse(null);
    }

    // Delegates to TrainerService
    public Trainer createTrainer(String firstName, String lastName) {
        return trainerService.create(firstName, lastName);
    }

    public Trainer findTrainer(String username) {
        return trainerService.find(username).orElse(null);
    }

    // Delegates to TrainingService
    public Training createTraining(String name, String traineeUsername, String trainerUsername) {
        return trainingService.create(name, traineeUsername, trainerUsername);
    }

    public Training findTraining(Long id) {
        return trainingService.find(id).orElse(null);
    }
}
