package com.epam.gym.service;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.domain.Trainer;
import com.epam.gym.util.CredentialGenerator;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of TrainerService using a TrainerDao.
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDao trainerDao;
    private long nextId = 1L;

    @Autowired
    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
        // initialize nextId from existing data
        trainerDao.findAll()
                .stream()
                .mapToLong(Trainer::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    @Override
    @Transactional
    public Trainer create(String firstName, String lastName) {
        // given
        String base = firstName + "." + lastName;
        String username = base;
        int suffix = 0;
        // when
        while (trainerDao.findByUsername(username).isPresent()) {
            username = base + (++suffix);
        }
        String password = CredentialGenerator.randomPassword();
        Trainer t = new Trainer(nextId++, firstName, lastName, username, password);
        // then
        return trainerDao.save(t);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trainer> find(String username) {
        // given / when / then
        return trainerDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Trainer> findAll() {
        // given / when / then
        return trainerDao.findAll();
    }
}
