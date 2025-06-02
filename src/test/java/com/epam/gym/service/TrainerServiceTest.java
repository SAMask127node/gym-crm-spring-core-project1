package com.epam.gym.service;

import com.epam.gym.config.StorageConfig;
import com.epam.gym.domain.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies TrainerService functionality using an in‐memory map (StorageConfig).
 */
@SpringJUnitConfig(StorageConfig.class)
class TrainerServiceTest {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    @Qualifier("trainerStorage")
    private java.util.Map<String, Trainer> trainerMap;

    @BeforeEach
    void cleanup() {
        trainerMap.clear();
    }

    @Test
    void testCreateAndFind() {
        Trainer t = trainerService.create("Grace", "Hopper");
        assertNotNull(t, "Returned Trainer should not be null");
        assertEquals("Grace.Hopper", t.username(), "Username should be first.last");
        assertNotNull(t.password(), "Password should be non‐null");

        Optional<Trainer> opt = trainerService.find(t.username());
        assertTrue(opt.isPresent(), "Trainer must be found by username");
        assertEquals("Hopper", opt.get().lastName(), "Last name should match");
    }

    @Test
    void testUniqueUsernameSuffix() {
        Trainer t1 = trainerService.create("Alan", "Turing");
        Trainer t2 = trainerService.create("Alan", "Turing");
        assertEquals("Alan.Turing", t1.username(), "First Alan.Turing");
        assertEquals("Alan.Turing1", t2.username(), "Second should get suffix '1'");
    }

    @Test
    void testFindAll() {
        long before = trainerService.findAll().size();
        trainerService.create("Linus", "Torvalds");
        long after = trainerService.findAll().size();
        assertEquals(before + 1, after, "findAll() size should increment by 1");
    }
}
