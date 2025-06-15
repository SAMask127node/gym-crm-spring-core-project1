package com.epam.gym.service;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.domain.Trainee;
import com.epam.gym.util.CredentialGenerator;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao dao;
    private long nextId = 1L;

    @Autowired
    public TraineeServiceImpl(TraineeDao dao) {
        this.dao = dao;
        // If you ever pre-seed data, initialize nextId here via dao.findAll()
    }

    @Override
    @Transactional
    public Trainee create(String first, String last) {
        String base = first + "." + last;
        String username = base;
        int suffix = 0;
        while (dao.findByUsername(username).isPresent()) {
            username = base + (++suffix);
        }
        String pw = CredentialGenerator.randomPassword();
        Trainee t = new Trainee(null, first, last, username, pw);
        return dao.save(t);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trainee> find(String username) {
        return dao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Trainee> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public void delete(String username) {
        dao.deleteByUsername(username);
    }
}
