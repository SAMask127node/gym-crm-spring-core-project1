package com.epam.gym.service.impl;

import com.epam.gym.domain.Trainee;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.dto.Credentials;
import com.epam.gym.dao.TraineeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired private TraineeDao dao;

    @Override
    @Transactional
    public Credentials create(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        // TODO: generate username/password, save via DAO
        return dao.create(firstName, lastName, dateOfBirth, address);
    }
    @Override @Transactional public void changeLogin(String oldUsername, String newUsername) { dao.changeLogin(oldUsername, newUsername); }
    @Override public Trainee getProfile(String username) { return dao.findByUsername(username); }
    @Override @Transactional public Trainee update(String username, String first, String last, LocalDate dob, String addr) { return dao.update(username, first, last, dob, addr); }
    @Override @Transactional public void delete(String username) { dao.deleteByUsername(username); }
    @Override @Transactional public void assignTrainer(String traineeUsername, String trainerUsername) { dao.assignTrainer(traineeUsername, trainerUsername); }
    @Override public List<String> getTrainers(String traineeUsername) { return dao.findTrainers(traineeUsername); }
}