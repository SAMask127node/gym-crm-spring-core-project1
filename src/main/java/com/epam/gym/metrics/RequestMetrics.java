package com.epam.gym.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RequestMetrics {
    private final MeterRegistry registry;
    private Counter loginCounter;

    public RequestMetrics(MeterRegistry registry) {
        this.loginCounter = registry.counter("api_login_requests_total");
    }

    public void incrementLogin() {
        loginCounter.increment();
    }
}
