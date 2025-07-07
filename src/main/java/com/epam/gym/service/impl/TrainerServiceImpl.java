package com.epam.gym.service.impl;

import com.epam.gym.domain.Trainer;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.dto.Credentials;
import com.epam.gym.dao.TrainerDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao dao;

    public TrainerServiceImpl(TrainerDao dao) {
        this.dao = dao;
    }


    @Override
    @Transactional
    public Credentials create(String firstName, String lastName, String specialization) {
        // 1) Generate username/password
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 0;
        while (dao.findByUsername(username).isPresent()) {
            username = baseUsername + (++suffix);
        }
        String password = UUID.randomUUID().toString();

        // 2) Build the entity
        Trainer t = Trainer.builder()
                .id(nextId++)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .specialization(specialization)
                .active(true)
                .build();

        // 3) Persist via DAO
        dao.save(t);

        // 4) Return only credentials
        return new Credentials(username, password);
    }
    @Override public Trainer getProfile(String username) { return dao.findByUsername(username); }
    @Override @Transactional public Trainer update(String username, String first, String last, String spec) { return dao.update(username, first, last, spec); }
    @Override public List<String> getTrainees(String trainerUsername) { return dao.findTrainees(trainerUsername); }
}
