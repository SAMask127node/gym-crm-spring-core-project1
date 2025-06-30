package com.epam.gym.service.impl;

import com.epam.gym.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired private com.epam.gym.dao.UserDao userDao;

    @Override
    public void authenticate(String username, String password) {
        if (!userDao.checkCredentials(username, password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}
