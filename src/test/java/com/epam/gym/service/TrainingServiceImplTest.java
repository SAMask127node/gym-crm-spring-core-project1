package com.epam.gym.service;

import com.epam.gym.dao.inmemory.InMemoryTrainingDao;
import com.epam.gym.domain.Training;
import com.epam.gym.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class TrainingServiceImplTest {
    private TrainingService service;

    @BeforeEach
    void setUp() {
        var dao = new InMemoryTrainingDao();
        service = new TrainingServiceImpl(dao);
    }

    @Test
    void addAndListByTrainee() {
        // given
        Training t1 = service.create("Yoga","u1","tr1", LocalDate.now(), 60, "YOGA");

        // when
        List<Training> list = service.findByTrainee("u1");

        // then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getId()).isEqualTo(t1.getId());
    }

    @Test
    void activateDeactivate() {
        // given
        Training t1 = service.create("Run","u2","tr2",LocalDate.now(),30,"CARDIO");

        // when
        String s1 = service.deactivate(t1.getId());
        String s2 = service.activate(t1.getId());

        // then
        assertThat(s1).isEqualTo("inactive");
        assertThat(s2).isEqualTo("active");
    }
}