package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.UserDao;
import com.epam.gym.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserDao implements UserDao {
    private final Map<Long, User> byId = new HashMap<>();
    private final Map<String, User> byUsername = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(byUsername.get(username));
    }

    @Override
    public User create(User user) {
        long id = idGen.incrementAndGet();
        user.setId(id);
        byId.put(id, user);
        byUsername.put(user.getUsername(), user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        User u = byId.remove(id);
        if (u != null) {
            byUsername.remove(u.getUsername());
        }
    }

    @Override
    public void deleteAll() {
        byId.clear();
        byUsername.clear();
        idGen.set(0);
    }

    @Override
    public boolean checkCredentials(String username, String rawPassword) {
        return findByUsername(username)
                .map(u -> u.getPassword().equals(rawPassword))
                .orElse(false);
    }
}
