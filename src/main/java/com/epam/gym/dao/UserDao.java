// src/main/java/com/epam/gym/dao/UserDao.java
package com.epam.gym.dao;

import com.epam.gym.domain.User;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    User create(User user);
    boolean checkCredentials(String username, String rawPassword);
    void deleteById(Long id);
    void deleteAll();
}
