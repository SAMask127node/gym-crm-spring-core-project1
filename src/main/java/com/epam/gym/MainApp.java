package com.epam.gym;

import com.epam.gym.config.AppConfig;
import com.epam.gym.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade facade = ctx.getBean(GymFacade.class);

        // Now facade.createTrainee(...) will work,
        // because Spring has already instantiated InMemoryTraineeDao, TraineeServiceImpl, and injected them.
        var t = facade.createTrainee("John", "Doe");
        System.out.println("Created trainee: " + t.username());
    }
}