package com.epam.gym.controller;

import com.epam.gym.dto.*;
import com.epam.gym.domain.Trainee;
import com.epam.gym.service.TraineeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trainees")
@RequiredArgsConstructor
@Api(tags = "Trainee API")
@Validated
public class TraineeController {
    private static final Logger log = LoggerFactory.getLogger(TraineeController.class);
    private final TraineeService traineeService;

    private String tx() { return MDC.get("transactionId"); }

    @PostMapping
    @ApiOperation("Create new trainee")
    public TraineeRegResp create(@Valid @RequestBody TraineeRegReq request) {
        log.info("[{}] POST /api/trainees {}", tx(), request);
        var creds = traineeService.create(
            request.getFirstName(), 
            request.getLastName(), 
            request.getDateOfBirth(), 
            request.getAddress()
        );
        var response = TraineeRegResp.builder()
                .username(creds.username())
                .password(creds.password())
                .build();
        log.info("[{}] RESP 200 TraineeRegResp", tx());
        return response;
    }

    @GetMapping("/{username}")
    @ApiOperation("Get trainee profile")
    public TraineeProfileResp get(@PathVariable String username) {
        log.info("[{}] GET /api/trainees/{}", tx(), username);
        var trainee = traineeService.getProfile(username);
        var response = TraineeProfileResp.builder()
                .username(trainee.getUsername())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
        log.info("[{}] RESP 200 TraineeProfileResp", tx());
        return response;
    }

    @PutMapping("/{username}")
    @ApiOperation("Update trainee profile")
    public TraineeProfileResp update(
            @PathVariable String username,
            @Valid @RequestBody UpdateTraineeReq request
    ) {
        log.info("[{}] PUT /api/trainees/{} {}", tx(), username, request);
        var trainee = traineeService.update(
            username,
            request.getFirstName(),
            request.getLastName(),
            request.getDateOfBirth(),
            request.getAddress()
        );
        var response = TraineeProfileResp.builder()
                .username(trainee.getUsername())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
        log.info("[{}] RESP 200 TraineeProfileResp", tx());
        return response;
    }

    @DeleteMapping("/{username}")
    @ApiOperation("Delete trainee")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        log.info("[{}] DELETE /api/trainees/{}", tx(), username);
        traineeService.delete(username);
        log.info("[{}] RESP 200 OK delete trainee", tx());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/trainers")
    @ApiOperation("List trainers of a trainee")
    public TraineeTrainersResp listTrainers(@PathVariable String username) {
        log.info("[{}] GET /api/trainees/{}/trainers", tx(), username);
        var trainers = traineeService.getTrainers(username);
        var response = TraineeTrainersResp.builder()
                .traineeUsername(username)
                .trainers(trainers)
                .build();
        log.info("[{}] RESP 200 TraineeTrainersResp", tx());
        return response;
    }

    @GetMapping("/{username}/trainings")
    @ApiOperation("List trainings by trainee")
    public TraineeTrainingsResp listTrainings(@PathVariable String username) {
        log.info("[{}] GET /api/trainees/{}/trainings", tx(), username);
        var trainings = traineeService.getTrainings(username);
        var trainingNames = trainings.stream()
                .map(training -> training.getName())
                .collect(Collectors.toList());
        var response = TraineeTrainingsResp.builder()
                .traineeUsername(username)
                .trainingNames(trainingNames)
                .build();
        log.info("[{}] RESP 200 TraineeTrainingsResp", tx());
        return response;
    }
}