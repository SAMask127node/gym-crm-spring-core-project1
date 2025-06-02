package com.epam.gym.config;

import com.epam.gym.domain.Trainee;
import com.epam.gym.domain.Trainer;
import com.epam.gym.domain.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import jakarta.annotation.PostConstruct;
import  org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.epam.gym",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = ".*Test"
        )
)
@PropertySource("classpath:application.properties")
public class StorageConfig {
    private static final Logger logger = LoggerFactory.getLogger(StorageConfig.class);

    @Value("${trainee.data.path}")
    private Resource traineeData;

    @Value("${trainer.data.path}")
    private Resource trainerData;

    @Value("${training.data.path}")
    private Resource trainingData;

    @Bean(name = "traineeStorage")
    public Map<String, Trainee> traineeStorage() {
        return new HashMap<>();
    }

    @Bean(name = "trainerStorage")
    public Map<String, Trainer> trainerStorage() {
        return new HashMap<>();
    }

    @Bean(name = "trainingStorage")
    public Map<Long, Training> trainingStorage() {
        return new HashMap<>();
    }

    @PostConstruct
    public void loadInitialData() {
        loadTrainees();
        loadTrainers();
        loadTrainings();
    }

    private void loadTrainees() {
        try (var in = new BufferedReader(new InputStreamReader(traineeData.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            Map<String, Trainee> map = traineeStorage();
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",");
                Long id = Long.valueOf(parts[0]);
                String first = parts[1];
                String last = parts[2];
                String username = first + "." + last;
                map.put(username, new Trainee(id, first, last, username, null));
                logger.info("Loaded Trainee: {}", username);
            }
        } catch (Exception e) {
            logger.warn("Could not load trainees CSV: {}", e.getMessage());
        }
    }

    private void loadTrainers() {
        try (var in = new BufferedReader(new InputStreamReader(trainerData.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            Map<String, Trainer> map = trainerStorage();
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",");
                Long id = Long.valueOf(parts[0]);
                String first = parts[1];
                String last = parts[2];
                String username = first + "." + last;
                map.put(username, new Trainer(id, first, last, username, null));
                logger.info("Loaded Trainer: {}", username);
            }
        } catch (Exception e) {
            logger.warn("Could not load trainers CSV: {}", e.getMessage());
        }
    }

    private void loadTrainings() {
        try (var in = new BufferedReader(new InputStreamReader(trainingData.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            Map<Long, Training> map = trainingStorage();
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",");
                Long id = Long.valueOf(parts[0]);
                String name = parts[1];
                String traineeUsername = parts[2];
                String trainerUsername = parts[3];
                map.put(id, new Training(id, name, traineeUsername, trainerUsername));
                logger.info("Loaded Training: {} (Trainee={}, Trainer={})", name, traineeUsername, trainerUsername);
            }
        } catch (Exception e) {
            logger.warn("Could not load trainings CSV: {}", e.getMessage());
        }
    }
}
