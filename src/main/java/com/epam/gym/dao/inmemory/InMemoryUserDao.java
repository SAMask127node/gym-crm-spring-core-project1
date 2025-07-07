package com.epam.gym.dao.inmemory;

import com.epam.gym.dao.UserDao;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserDao implements UserDao {
    private static final Map<String, String> users = new ConcurrentHashMap<>();

    public static void addUser(String username, String password) {
        users.put(username, password);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
