package com.epam.gym.dao;

import com.epam.gym.domain.Trainer;
import com.epam.gym.service.dto.Credentials;
import java.util.List;

public interface TrainerDao {
    Credentials create(String firstName, String lastName, String specialization);
    Trainer findByUsername(String username);
    Trainer update(String username, String firstName, String lastName, String specialization);
    List<String> findTrainees(String trainerUsername);
}