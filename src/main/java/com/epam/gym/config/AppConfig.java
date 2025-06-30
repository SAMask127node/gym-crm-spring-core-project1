package com.epam.gym.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main Spring configuration: component‐scans the DAO, Service, and Facade packages.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.gym.dao",
        "com.epam.gym.service",
        "com.epam.gym.facade"
})
public class AppConfig {
    // No explicit @Bean methods needed, since we rely on @Repository/@Service/@Component.
}