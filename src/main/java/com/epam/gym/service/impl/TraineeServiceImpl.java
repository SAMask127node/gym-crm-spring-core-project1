package com.epam.gym.service.impl;

import com.epam.gym.domain.Trainee;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.dto.Credentials;
import com.epam.gym.dao.TraineeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao dao;

    public TraineeServiceImpl(TraineeDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public Credentials create(String firstName,
                              String lastName,
                              LocalDate dateOfBirth,
                              String address) {
        // 1) Business logic: generate username/password
        String username = generateUsername(firstName, lastName);
        String password = generateRandomPassword();

        // 2) Build the entity
        Trainee t = Trainee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .active(true)
                .build();

        // 3) Persist via DAO
        dao.save(t);

        // 4) Return only the credentials
        return new Credentials(username, password);
    }

    @Override @Transactional public void changeLogin(String oldUsername, String newUsername) { dao.changeLogin(oldUsername, newUsername); }
    @Override public Trainee getProfile(String username) { return dao.findByUsername(username); }
    @Override @Transactional public Trainee update(String username, String first, String last, LocalDate dob, String addr) { return dao.update(username, first, last, dob, addr); }
    @Override @Transactional public void delete(String username) { dao.deleteByUsername(username); }
    @Override @Transactional public void assignTrainer(String traineeUsername, String trainerUsername) { dao.assignTrainer(traineeUsername, trainerUsername); }
    @Override public List<String> getTrainers(String traineeUsername) { return dao.findTrainers(traineeUsername); }
}