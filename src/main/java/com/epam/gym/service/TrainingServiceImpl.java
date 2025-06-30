package com.epam.gym.service;

import com.epam.gym.dao.TrainingDao;
import com.epam.gym.domain.Training;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of TrainingService using a TrainingDao.
 */
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDao trainingDao;
    private long nextId = 1L;

    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
        // initialize nextId from existing data
        trainingDao.findAll()
                .stream()
                .mapToLong(Training::id)
                .max()
                .ifPresent(max -> nextId = max + 1);
    }

    @Override
    @Transactional
    public Training create(String name, String traineeUsername, String trainerUsername) {
        // given
        Long id = nextId++;
        Training t = new Training(id, name, traineeUsername, trainerUsername);
        // when / then
        return trainingDao.save(t);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Training> find(Long id) {
        // given / when / then
        return trainingDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Training> findAll() {
        // given / when / then
        return trainingDao.findAll();
    }
}
