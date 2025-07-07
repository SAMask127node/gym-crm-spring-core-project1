package com.epam.gym.service;

import com.epam.gym.dao.inmemory.InMemoryTraineeDao;
import com.epam.gym.domain.Trainee;
import com.epam.gym.service.dto.Credentials;
import com.epam.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class TraineeServiceImplTest {
    private TraineeService service;

    @BeforeEach
    void setUp() {
        // given: in-memory DAO and service
        var dao = new InMemoryTraineeDao();
        service = new TraineeServiceImpl(dao);
    }

    @Test
    void createAndFind() {
        // when
        Credentials creds = service.create("John", "Doe", LocalDate.of(1990,1,1), "Addr");
        Trainee t = service.getProfile(creds.username());

        // then
        assertThat(t).isNotNull();
        assertThat(t.getUsername()).isEqualTo(creds.username());
        assertThat(t.getFirstName()).isEqualTo("John");
        assertThat(t.getLastName()).isEqualTo("Doe");
    }

    @Test
    void changeLogin() {
        // given
        Credentials c = service.create("Jane", "Smith", null, null);

        // when
        service.changeLogin(c.username(), "jane.smith");
        Trainee t = service.getProfile("jane.smith");

        // then
        assertThat(t).isNotNull();
        assertThat(t.getUsername()).isEqualTo("jane.smith");
    }

    @Test
    void assignTrainerAndList() {
        // given
        Credentials c = service.create("A","B",null,null);

        // when
        service.assignTrainer(c.username(), "coach1");
        List<String> trainers = service.getTrainers(c.username());

        // then
        assertThat(trainers).containsExactly("coach1");
    }
}