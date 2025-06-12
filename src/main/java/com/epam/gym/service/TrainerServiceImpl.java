package com.epam.gym.service;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.domain.Trainer;
import com.epam.gym.util.CredentialGenerator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private long nextId = 1L;

    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
        trainerDao.findAll()
                .stream()
                .mapToLong(Trainer::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    @Override
    public Trainer create(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 0;
        while (trainerDao.findByUsername(username).isPresent()) {
            suffix++;
            username = baseUsername + suffix;
        }
        String password = CredentialGenerator.randomPassword();
        Trainer t = new Trainer(nextId++, firstName, lastName, username, password);
        return trainerDao.save(t);
    }

    @Override
    public Optional<Trainer> find(String username) {
        return trainerDao.findByUsername(username);
    }

    @Override
    public Collection<Trainer> findAll() {
        return trainerDao.findAll();
    }
}
