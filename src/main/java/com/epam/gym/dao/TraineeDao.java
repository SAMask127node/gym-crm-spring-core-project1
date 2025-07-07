package com.epam.gym.dao;

import com.epam.gym.domain.Trainee;
import com.epam.gym.service.dto.Credentials;
import java.time.LocalDate;
import java.util.List;

public interface TraineeDao {
    Trainee save(Trainee trainee);
    Credentials create(String firstName, String lastName, LocalDate dateOfBirth, String address);
    void changeLogin(String oldUsername, String newUsername);
    Trainee findByUsername(String username);
    Trainee update(String username, String firstName, String lastName, LocalDate dateOfBirth, String address);
    void deleteByUsername(String username);
    void assignTrainer(String traineeUsername, String trainerUsername);
    List<String> findTrainers(String traineeUsername);
}
