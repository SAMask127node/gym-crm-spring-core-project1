package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.TrainingDao;
import com.epam.gym.domain.Training;
import com.epam.gym.domain.TrainingType;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTrainingDao implements TrainingDao {
    private static final Map<Long, Training> trainings = new ConcurrentHashMap<>();
    private static final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Training create(String name, String traineeUsername, String trainerUsername, LocalDate date, int duration, String typeCode) {
        Long id = idGen.incrementAndGet();
        Training t = new Training();
        t.setId(id);
        t.setName(name);
        t.setTraineeUsername(traineeUsername);
        t.setTrainerUsername(trainerUsername);
        t.setDate(date);
        t.setDuration(duration);
        t.setTypeCode(typeCode);
        t.setActive(true);
        trainings.put(id, t);
        return t;
    }

    @Override
    public List<Training> findByTrainee(String traineeUsername) {
        List<Training> list = new ArrayList<>();
        trainings.values().forEach(t -> {
            if (t.getTraineeUsername().equals(traineeUsername)) list.add(t);
        });
        return list;
    }

    @Override
    public List<Training> findByTrainer(String trainerUsername) {
        List<Training> list = new ArrayList<>();
        trainings.values().forEach(t -> {
            if (t.getTrainerUsername().equals(trainerUsername)) list.add(t);
        });
        return list;
    }

    @Override
    public String activate(Long id) {
        Training t = trainings.get(id);
        if (t != null) {
            t.setActive(true);
            return "active";
        }
        return null;
    }

    @Override
    public String deactivate(Long id) {
        Training t = trainings.get(id);
        if (t != null) {
            t.setActive(false);
            return "inactive";
        }
        return null;
    }

    @Override
    public List<TrainingType> listTypes() {
        // example types
        return List.of(
                new TrainingType("YOGA", "Yoga session"),
                new TrainingType("CARDIO", "Cardio workout"),
                new TrainingType("STRENGTH", "Strength training")
        );
    }
}