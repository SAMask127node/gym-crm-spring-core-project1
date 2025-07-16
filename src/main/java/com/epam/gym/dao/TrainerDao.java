package com.epam.gym.dao;

import com.epam.gym.domain.Trainer;
import java.util.List;
import java.util.Optional;

public interface TrainerDao {
    Optional<Trainer> findById(Long id);
    Optional<Trainer> findByUsername(String username);
    List<Trainer> findAll();
    Trainer create(Trainer trainer);
    Trainer update(Trainer trainer);
    void deleteById(Long id);
    void deleteAll();
}
//package com.epam.gym.dao;
//
//import com.epam.gym.domain.Trainer;
//import com.epam.gym.service.dto.Credentials;
//import java.util.List;
//
//public interface TrainerDao {
//    Trainer save(Trainer trainer);
//    Credentials create(String firstName, String lastName, String specialization);
//    Trainer findByUsername(String username);
//    Trainer update(String username, String firstName, String lastName, String specialization);
//    List<String> findTrainees(String trainerUsername);
//}