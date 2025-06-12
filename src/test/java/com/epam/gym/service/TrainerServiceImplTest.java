package com.epam.gym.service;

import com.epam.gym.dao.TrainerDaoImpl;
import com.epam.gym.dao.TrainerDao;
import com.epam.gym.domain.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class TrainerServiceImplTest {

    private TrainerDao trainerDao;
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        // given: a fresh in‐memory DAO and service
        trainerDao = new TrainerDaoImpl();
        trainerDao.deleteAll(); // ensure empty
        trainerService = new TrainerServiceImpl(trainerDao);
    }

    @Test
    void testCreateAndFind() {
        // given
        String first = "Grace";
        String last = "Hopper";

        // when
        Trainer created = trainerService.create(first, last);

        // then
        assertThat(created).isNotNull();
        assertThat(created.username()).isEqualTo("Grace.Hopper");
        assertThat(created.password()).isNotBlank();

        Optional<Trainer> found = trainerService.find(created.username());
        assertThat(found).isPresent()
                .get()
                .extracting(Trainer::lastName)
                .isEqualTo("Hopper");
    }

    @Test
    void testUniqueUsernameSuffix() {
        // given
        trainerService.create("Alan", "Turing");

        // when
        Trainer second = trainerService.create("Alan", "Turing");

        // then
        assertThat(second.username()).isEqualTo("Alan.Turing1");
    }

    @Test
    void testFindAll() {
        // given
        trainerService.create("Linus", "Torvalds");

        // when
        var all = List.copyOf(trainerService.findAll());

        // then
        assertThat(all).hasSize(1)
                .extracting(Trainer::username)
                .containsExactly("Linus.Torvalds");
    }
}
