package com.epam.gym.service.impl;

import com.epam.gym.domain.Training;
import com.epam.gym.domain.TrainingType;
import com.epam.gym.service.TrainingService;
import com.epam.gym.dao.TrainingDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao dao;

    public TrainingServiceImpl(TrainingDao dao) {
        this.dao = dao;
    }


    @Override
    @Transactional
    public Training create(String name,
                           String traineeUsername,
                           String trainerUsername) {
        // build fully-formed entity
        Training t = Training.builder()
                .id(nextId++)
                .name(name)
                .traineeUsername(traineeUsername)
                .trainerUsername(trainerUsername)
                .active(true)
                .build();

        // hand off to DAO to persist
        return dao.save(t);
    }
    @Override public List<Training> findByTrainee(String traineeUsername) { return dao.findByTrainee(traineeUsername); }
    @Override public List<Training> findByTrainer(String trainerUsername) { return dao.findByTrainer(trainerUsername); }
    @Override @Transactional public String activate(Long id) { return dao.activate(id); }
    @Override @Transactional public String deactivate(Long id) { return dao.deactivate(id); }
    @Override public List<TrainingType> getTypes() { return dao.listTypes(); }
}
