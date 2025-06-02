package com.epam.gym.domain;

public record Trainee(
        Long id,
        String firstName,
        String lastName,
        String username,
        String password
) { }