package com.epam.gym.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // custom check, e.g. attempt a lightweight query
        boolean dbUp = true; // replace with real logic
        return dbUp ? Health.up().withDetail("db", "Available").build()
                : Health.down().withDetail("db", "Not Available").build();
    }
}