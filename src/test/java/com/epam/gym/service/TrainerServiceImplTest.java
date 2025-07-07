package com.epam.gym.service;

import com.epam.gym.dao.inmemory.InMemoryTrainerDao;
import com.epam.gym.domain.Trainer;
import com.epam.gym.service.dto.Credentials;
import com.epam.gym.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.List;

class TrainerServiceImplTest {
    private TrainerService service;

    @BeforeEach
    void setUp() {
        var dao = new InMemoryTrainerDao();
        service = new TrainerServiceImpl(dao);
    }

    @Test
    void createAndFind() {
        Credentials creds = service.create("Alan","Turing","CS");
        Trainer t = service.getProfile(creds.username());

        assertThat(t).isNotNull();
        assertThat(t.getUsername()).isEqualTo(creds.username());
        assertThat(t.getSpecialization()).isEqualTo("CS");
    }

    @Test
    void updateTrainer() {
        Credentials creds = service.create("Ada","Lovelace","Math");
        Trainer t2 = service.update(creds.username(), "Ada","Lovelace","Physics");

        assertThat(t2.getSpecialization()).isEqualTo("Physics");
    }

    @Test
    void listTraineesEmpty() {
        List<String> list = service.getTrainees("nonexist");
        assertThat(list).isEmpty();
    }
}