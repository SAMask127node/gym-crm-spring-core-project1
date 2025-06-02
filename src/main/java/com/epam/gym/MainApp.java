package com.epam.gym;

import com.epam.gym.config.StorageConfig;
import com.epam.gym.domain.Trainee;
import com.epam.gym.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(StorageConfig.class);
        var facade = ctx.getBean(GymFacade.class);

        // Example usage:
        Trainee t = facade.addTrainee("Mark", "Twain");
        logger.info("New Trainee: {} / {}", t.username(), t.password());

        ctx.close();
    }
}