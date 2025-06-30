package com.epam.gym.domain;

public record Trainer(
        Long id,
        String firstName,
        String lastName,
        String username,
        String password
) { }