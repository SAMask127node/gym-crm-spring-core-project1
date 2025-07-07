package com.epam.gym.service.impl;

import com.epam.gym.exception.AuthenticationException;
import com.epam.gym.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDao userDao;

    public AuthenticationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void authenticate(String username, String password) {
        if (!userDao.checkCredentials(username, password)) {
            throw new AuthenticationException("Invalid credentials");
        }
    }

}
