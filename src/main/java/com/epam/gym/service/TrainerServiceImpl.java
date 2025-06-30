package com.epam.gym.service.impl;

import com.epam.gym.domain.Trainer;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.dto.Credentials;
import com.epam.gym.dao.TrainerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired private TrainerDao dao;

    @Override
    @Transactional
    public Credentials create(String firstName, String lastName, String specialization) {
        return dao.create(firstName, lastName, specialization);
    }
    @Override public Trainer getProfile(String username) { return dao.findByUsername(username); }
    @Override @Transactional public Trainer update(String username, String first, String last, String spec) { return dao.update(username, first, last, spec); }
    @Override public List<String> getTrainees(String trainerUsername) { return dao.findTrainees(trainerUsername); }
}
