package com.epam.gym.service;

import com.epam.gym.config.StorageConfig;
import com.epam.gym.domain.Training;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies TrainingService functionality using an in‐memory map (StorageConfig).
 */
@SpringJUnitConfig(StorageConfig.class)
class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @BeforeEach
    void cleanup() {
        // No need to explicitly clear the map; StorageConfig is reloaded once per test class.
    }

    @Test
    void testCreateAndFind() {
        Training t = trainingService.create("YogaBasics", "Alice.Wonderland", "Grace.Hopper");
        assertNotNull(t, "Returned Training should not be null");
        assertEquals("YogaBasics", t.name(), "Name should match");
        assertEquals("Alice.Wonderland", t.traineeUsername(), "Trainee username should match");

        Optional<Training> opt = trainingService.find(t.id());
        assertTrue(opt.isPresent(), "Training must be found by ID");
        assertEquals("Grace.Hopper", opt.get().trainerUsername(), "Trainer username should match");
    }

    @Test
    void testFindAllIncrement() {
        long before = trainingService.findAll().size();
        trainingService.create("Pilates", "Bob.Builder", "Alan.Turing");
        long after = trainingService.findAll().size();
        assertEquals(before + 1, after, "findAll() size should increment by 1");
    }
}
