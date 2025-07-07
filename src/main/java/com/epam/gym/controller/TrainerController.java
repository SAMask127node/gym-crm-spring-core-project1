package com.epam.gym.controller;

import com.epam.gym.dto.*;
import com.epam.gym.domain.Trainer;
import com.epam.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private String tx() { return MDC.get("transactionId"); }

    @PostMapping
    public TrainerRegResp create(@Valid @RequestBody TrainerRegReq request) {
        var creds = trainerService.create(request.getFirstName(), request.getLastName(), request.getSpecialization());
        return TrainerRegResp.builder()
                .username(creds.getUsername())
                .password(creds.getPassword())
                .build();
    }

    @GetMapping("/{username}")
    public TrainerProfileResp get(@PathVariable String username) {
        var t = trainerService.getProfile(username);
        return TrainerProfileResp.builder()
                .username(t.getUsername())
                .firstName(t.getFirstName())
                .lastName(t.getLastName())
                .specialization(t.getSpecialization())
                .build();
    }

    @PutMapping("/{username}")
    public TrainerProfileResp update(@PathVariable String username, @Valid @RequestBody UpdateTrainerReq request) {
        var t = trainerService.update(username, request.getFirstName(), request.getLastName(), request.getSpecialization());
        return TrainerProfileResp.builder()
                .username(t.getUsername())
                .firstName(t.getFirstName())
                .lastName(t.getLastName())
                .specialization(t.getSpecialization())
                .build();
    }

    @GetMapping("/{username}/trainees")
    public TrainerTraineesResp listTrainees(@PathVariable String username) {
        List<String> trainees = trainerService.getTrainees(username);
        return TrainerTraineesResp.builder()
                .trainerUsername(username)
                .trainees(trainees)
                .build();
    }

    @GetMapping("/{username}/trainings")
    public TrainerTrainingsResp listTrainings(@PathVariable String username) {
        List<String> trainingNames = trainerService.getTrainings(username)
                .stream().map(t -> t.getName()).collect(Collectors.toList());
        return TrainerTrainingsResp.builder()
                .trainerUsername(username)
                .trainingNames(trainingNames)
                .build();
    }
}