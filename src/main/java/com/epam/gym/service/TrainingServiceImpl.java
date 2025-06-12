package com.epam.gym.service;

import com.epam.gym.dao.TrainingDao;
import com.epam.gym.domain.Training;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao trainingDao;
    private long nextId = 1L;

    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
        trainingDao.findAll()
                .stream()
                .mapToLong(Training::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    @Override
    public Training create(String name, String traineeUsername, String trainerUsername) {
        Long id = nextId++;
        Training t = new Training(id, name, traineeUsername, trainerUsername);
        return trainingDao.save(t);
    }

    @Override
    public Optional<Training> find(Long id) {
        return trainingDao.findById(id);
    }

    @Override
    public Collection<Training> findAll() {
        return trainingDao.findAll();
    }
}
