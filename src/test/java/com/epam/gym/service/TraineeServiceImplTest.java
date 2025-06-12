package com.epam.gym.service;

import com.epam.gym.dao.TraineeDaoImpl;
import com.epam.gym.dao.TraineeDao;
import com.epam.gym.domain.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class TraineeServiceImplTest {

    private TraineeDao traineeDao;
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        // given: a fresh in‐memory DAO and service
        traineeDao = new TraineeDaoImpl();
        traineeDao.deleteAll(); // ensure empty
        traineeService = new TraineeServiceImpl(traineeDao);
    }

    @Test
    void testCreateAndFind() {
        // given
        String first = "Alice";
        String last = "Wonderland";

        // when
        Trainee created = traineeService.create(first, last);

        // then
        assertThat(created).isNotNull();
        assertThat(created.username()).isEqualTo("Alice.Wonderland");
        assertThat(created.password()).isNotBlank();

        Optional<Trainee> found = traineeService.find(created.username());
        assertThat(found).isPresent()
                .get()
                .extracting(Trainee::firstName, Trainee::lastName)
                .containsExactly("Alice", "Wonderland");
    }

    @Test
    void testUniqueUsernameSuffix() {
        // given
        traineeService.create("Bob", "Builder");

        // when
        Trainee second = traineeService.create("Bob", "Builder");

        // then
        assertThat(second.username()).isEqualTo("Bob.Builder1");
    }

    @Test
    void testUpdateKeepsPassword() {
        // given
        Trainee original = traineeService.create("Carl", "Sagan");
        String uname = original.username();

        // when
        Trainee updated = traineeService.update(uname, "Carl", "Jung");

        // then
        assertThat(updated.username()).isEqualTo(uname);
        assertThat(updated.password()).isEqualTo(original.password());
        assertThat(updated.firstName()).isEqualTo("Carl");
        assertThat(updated.lastName()).isEqualTo("Jung");
    }

    @Test
    void testDeleteAndFindAll() {
        // given
        Trainee t1 = traineeService.create("Diana", "Prince");
        Trainee t2 = traineeService.create("Eve", "Polastri");

        // when
        traineeService.delete(t1.username());

        // then
        List<Trainee> all = List.copyOf(traineeService.findAll());
        assertThat(all).extracting(Trainee::username).doesNotContain(t1.username());

        // delete nonexistent should throw
        assertThatThrownBy(() -> traineeService.delete("non.existing"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such Trainee");
    }
}
