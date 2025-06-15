package com.epam.gym.domain;

public record Training(
        Long id,
        String name,
        String traineeUsername,
        String trainerUsername
) { }