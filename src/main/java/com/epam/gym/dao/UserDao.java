package com.epam.gym.dao;

public interface UserDao {
    boolean checkCredentials(String username, String password);
}
