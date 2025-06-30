package com.epam.gym;

import com.epam.gym.config.HibernateConfig;
import com.epam.gym.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(HibernateConfig.class);
        var facade = ctx.getBean(GymFacade.class);
        var t = facade.createTrainee("John", "Doe");
        System.out.println("Created: " + t.username());
    }
}
