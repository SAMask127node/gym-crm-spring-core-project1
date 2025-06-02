package com.epam.gym.service;

import com.epam.gym.config.StorageConfig;
import com.epam.gym.domain.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies TraineeService functionality using in‐memory maps (StorageConfig).
 */
@SpringJUnitConfig(StorageConfig.class)
class TraineeServiceTest {

    @Autowired
    private TraineeService traineeService;

    @BeforeEach
    void cleanup() {
        // Copy all existing usernames into a separate List first,
        // then delete them one by one. This avoids ConcurrentModificationException.
        var usernames = traineeService.findAll().stream()
                .map(Trainee::username)
                .toList();
    }
    @Test
    void testCreateAndFind() {
        Trainee t = traineeService.create("Alice", "Wonderland");
        assertNotNull(t, "Trainee.create(...) should not return null");
        assertEquals("Alice.Wonderland", t.username(), "Username must be first.last");
        assertNotNull(t.password(), "Password should be generated (non‐null)");

        Optional<Trainee> opt = traineeService.find(t.username());
        assertTrue(opt.isPresent(), "We should find the newly created trainee");
        assertEquals("Alice", opt.get().firstName());
        assertEquals("Wonderland", opt.get().lastName());
    }

    @Test
    void testUniqueUsernameSuffix() {
        Trainee t1 = traineeService.create("Bob", "Builder");
        Trainee t2 = traineeService.create("Bob", "Builder");
        assertEquals("Bob.Builder", t1.username(), "First Bob.Builder has no suffix");
        assertEquals("Bob.Builder1", t2.username(), "Second Bob.Builder gets suffix 1");
    }

    @Test
    void testUpdate() {
        Trainee original = traineeService.create("Carl", "Sagan");
        String uname = original.username();

        Trainee updated = traineeService.update(uname, "Carl", "Jung");
        assertEquals("Carl", updated.firstName());
        assertEquals("Jung", updated.lastName());
        assertEquals(uname, updated.username(), "Username must remain unchanged");
        assertEquals(original.password(), updated.password(), "Password stays the same");
    }

    @Test
    void testDelete() {
        Trainee t = traineeService.create("Diana", "Prince");
        String uname = t.username();
        traineeService.delete(uname);
        assertTrue(traineeService.find(uname).isEmpty(), "After delete, find() must return empty");
    }

    @Test
    void testFindAll() {
        long before = traineeService.findAll().size();
        traineeService.create("Eve", "Polastri");
        long after = traineeService.findAll().size();
        assertEquals(before + 1, after, "findAll() size should increase by exactly 1");
    }

    @Test
    void testDeleteNonexistentThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> traineeService.delete("does.not.exist"),
                "Deleting a trainee that does not exist must throw an IllegalArgumentException"
        );
    }
}
