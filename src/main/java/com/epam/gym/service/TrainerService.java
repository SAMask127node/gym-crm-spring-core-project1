package com.epam.gym.service;

import com.epam.gym.domain.Trainer;
import com.epam.gym.service.dto.Credentials;
import java.util.List;

public interface TrainerService {
    Credentials create(String firstName, String lastName, String specialization);
    Trainer getProfile(String username);
    Trainer update(String username, String firstName, String lastName, String specialization);
    List<String> getTrainees(String trainerUsername);
}