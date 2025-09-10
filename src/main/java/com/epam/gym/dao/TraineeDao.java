package com.epam.gym.dao;

import com.epam.gym.domain.Trainee;
import java.util.List;
import java.util.Optional;

public interface TraineeDao {
    Optional<Trainee> findById(Long id);
    Optional<Trainee> findByUsername(String username);
    List<Trainee> findAll();
    Trainee create(Trainee trainee);
    Trainee update(Trainee trainee);
    void deleteById(Long id);
    void deleteAll();
}

//public interface TraineeDao {
//    Trainee save(Trainee trainee);
//    Credentials create(String firstName, String lastName, LocalDate dateOfBirth, String address);
//    void changeLogin(String oldUsername, String newUsername);
//    Trainee findByUsername(String username);
//    Trainee update(String username, String firstName, String lastName, LocalDate dateOfBirth, String address);
//    void deleteByUsername(String username);
//    void assignTrainer(String traineeUsername, String trainerUsername);
//    List<String> findTrainers(String traineeUsername);
//}
