package com.epam.gym.service;

import com.epam.gym.dao.TrainingDao;
import com.epam.gym.dao.TrainingDaoImpl;
import com.epam.gym.domain.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class TrainingServiceImplTest {

    private TrainingDao trainingDao;
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        // given: a fresh in‐memory DAO and service
        trainingDao = new TrainingDaoImpl();
        trainingDao.deleteAll();
        trainingService = new TrainingServiceImpl(trainingDao);
    }

    @Test
    void testCreateAndFind() {
        // given
        String name = "YogaBasics";
        String traineeUsername = "Alice.Wonderland";
        String trainerUsername = "Grace.Hopper";

        // when
        Training created = trainingService.create(name, traineeUsername, trainerUsername);

        // then
        assertThat(created).isNotNull();
        assertThat(created.name()).isEqualTo("YogaBasics");
        assertThat(created.traineeUsername()).isEqualTo("Alice.Wonderland");

        Optional<Training> found = trainingService.find(created.id());
        assertThat(found).isPresent()
                .get()
                .extracting(Training::trainerUsername)
                .isEqualTo("Grace.Hopper");
    }

    @Test
    void testFindAllIncrement() {
        // given
        trainingService.create("Pilates", "Bob.Builder", "Alan.Turing");

        // when
        var all = List.copyOf(trainingService.findAll());

        // then
        assertThat(all).hasSize(1)
                .extracting(Training::name)
                .containsExactly("Pilates");
    }
}
