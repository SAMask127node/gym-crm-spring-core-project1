package com.epam.gym.service;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.domain.Trainee;
import com.epam.gym.util.CredentialGenerator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao traineeDao;
    private long nextId = 1L;

    public TraineeServiceImpl(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
        // init nextId from existing data
        traineeDao.findAll()
                .stream()
                .mapToLong(Trainee::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    @Override
    public Trainee create(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 0;
        while (traineeDao.findByUsername(username).isPresent()) {
            suffix++;
            username = baseUsername + suffix;
        }
        String password = CredentialGenerator.randomPassword();
        Trainee t = new Trainee(nextId++, firstName, lastName, username, password);
        return traineeDao.save(t);
    }

    @Override
    public Trainee update(String username, String newFirst, String newLast) {
        Trainee existing = traineeDao.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No such Trainee: " + username));
        Trainee updated = new Trainee(existing.id(), newFirst, newLast, existing.username(), existing.password());
        return traineeDao.save(updated);
    }

    @Override
    public void delete(String username) {
        if (traineeDao.findByUsername(username).isEmpty()) {
            throw new IllegalArgumentException("No such Trainee: " + username);
        }
        traineeDao.deleteByUsername(username);
    }

    @Override
    public Optional<Trainee> find(String username) {
        return traineeDao.findByUsername(username);
    }

    @Override
    public Collection<Trainee> findAll() {
        return traineeDao.findAll();
    }
}
