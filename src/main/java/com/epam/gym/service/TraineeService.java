package com.epam.gym.service;

import com.epam.gym.domain.Trainee;
import com.epam.gym.service.dto.Credentials;
import java.time.LocalDate;
import java.util.List;

public interface TraineeService {
    Credentials create(String firstName, String lastName, LocalDate dateOfBirth, String address);
    void changeLogin(String oldUsername, String newUsername);
    Trainee getProfile(String username);
    Trainee update(String username, String firstName, String lastName, LocalDate dateOfBirth, String address);
    void delete(String username);
    void assignTrainer(String traineeUsername, String trainerUsername);
    List<String> getTrainers(String traineeUsername);
}