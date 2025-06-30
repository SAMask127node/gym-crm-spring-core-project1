package com.epam.gym.controller;

import com.epam.gym.dto.*;
import com.epam.gym.filter.TransactionInterceptor;
import com.epam.gym.service.AuthenticationService;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(tags = "Gym CRM API")
@Validated
public class GymRestController {
    private static final Logger log = LoggerFactory.getLogger(GymRestController.class);

    @Autowired private TraineeService traineeService;
    @Autowired private TrainerService trainerService;
    @Autowired private TrainingService trainingService;
    @Autowired private AuthenticationService authService;

    private String tx() { return MDC.get(TransactionInterceptor.TX_ID); }

    @PostMapping("/trainees")
    @ApiOperation("Register new trainee")
    public TraineeRegResp registerTrainee(@Valid @RequestBody TraineeRegReq req) {
        log.info("[{}] POST /api/trainees {}", tx(), req);
        var creds = traineeService.create(req.firstName, req.lastName, req.dateOfBirth, req.address);
        var r = new TraineeRegResp(); r.username = creds.username(); r.password = creds.password();
        log.info("[{}] RESP 200 TraineeRegResp", tx());
        return r;
    }

    @PostMapping("/trainers")
    @ApiOperation("Register new trainer")
    public TrainerRegResp registerTrainer(@Valid @RequestBody TrainerRegReq req) {
        log.info("[{}] POST /api/trainers {}", tx(), req);
        var creds = trainerService.create(req.firstName, req.lastName, req.specialization);
        var r = new TrainerRegResp(); r.username = creds.username(); r.password = creds.password();
        log.info("[{}] RESP 200 TrainerRegResp", tx());
        return r;
    }

    @GetMapping("/login")
    @ApiOperation("Authenticate user")
    public ResponseEntity<Void> login(@Valid @ModelAttribute LoginReq req) {
        log.info("[{}] GET /api/login {}", tx(), req);
        authService.authenticate(req.username, req.password);
        log.info("[{}] RESP 200 OK login", tx());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/login")
    @ApiOperation("Change login")
    public ResponseEntity<Void> changeLogin(@Valid @RequestBody ChangeLoginReq req) {
        log.info("[{}] PUT /api/login {}", tx(), req);
        traineeService.changeLogin(req.oldUsername, req.newUsername);
        log.info("[{}] RESP 200 OK change login", tx());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trainees/{username}")
    @ApiOperation("Get trainee profile")
    public TraineeProfileResp getTrainee(@PathVariable String username) {
        log.info("[{}] GET /api/trainees/{}", tx(), username);
        var t = traineeService.getProfile(username);
        var r = new TraineeProfileResp();
        r.username = t.getUsername(); r.firstName = t.getFirstName(); r.lastName = t.getLastName();
        r.dateOfBirth = t.getDateOfBirth(); r.address = t.getAddress();
        log.info("[{}] RESP 200 TraineeProfileResp", tx());
        return r;
    }

    @PutMapping("/trainees/{username}")
    @ApiOperation("Update trainee profile")
    public TraineeProfileResp updateTrainee(@PathVariable String username,
                                            @Valid @RequestBody UpdateTraineeReq req) {
        log.info("[{}] PUT /api/trainees/{} {}", tx(), username, req);
        var t = traineeService.update(username, req.firstName, req.lastName, req.dateOfBirth, req.address);
        var r = new TraineeProfileResp();
        r.username = t.getUsername(); r.firstName = t.getFirstName(); r.lastName = t.getLastName();
        r.dateOfBirth = t.getDateOfBirth(); r.address = t.getAddress();
        log.info("[{}] RESP 200 TraineeProfileResp", tx());
        return r;
    }

    @DeleteMapping("/trainees/{username}")
    @ApiOperation("Delete trainee")
    public ResponseEntity<Void> deleteTrainee(@PathVariable String username) {
        log.info("[{}] DELETE /api/trainees/{}", tx(), username);
        traineeService.delete(username);
        log.info("[{}] RESP 200 OK delete trainee", tx());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trainers/{username}")
    @ApiOperation("Get trainer profile")
    public TrainerProfileResp getTrainer(@PathVariable String username) {
        log.info("[{}] GET /api/trainers/{}", tx(), username);
        var t = trainerService.getProfile(username);
        var r = new TrainerProfileResp();
        r.username = t.getUsername(); r.firstName = t.getFirstName(); r.lastName = t.getLastName(); r.specialization = t.getSpecialization();
        log.info("[{}] RESP 200 TrainerProfileResp", tx());
        return r;
    }

    @PutMapping("/trainers/{username}")
    @ApiOperation("Update trainer profile")
    public TrainerProfileResp updateTrainer(@PathVariable String username,
                                            @Valid @RequestBody UpdateTrainerReq req) {
        log.info("[{}] PUT /api/trainers/{} {}", tx(), username, req);
        var t = trainerService.update(username, req.firstName, req.lastName, req.specialization);
        var r = new TrainerProfileResp();
        r.username = t.getUsername(); r.firstName = t.getFirstName(); r.lastName = t.getLastName(); r.specialization = t.getSpecialization();
        log.info("[{}] RESP 200 TrainerProfileResp", tx());
        return r;
    }

    @PostMapping("/assignments")
    @ApiOperation("Assign trainer to trainee")
    public AssignmentResp assign(@Valid @RequestBody AssignmentReq req) {
        log.info("[{}] POST /api/assignments {}", tx(), req);
        traineeService.assignTrainer(req.traineeUsername, req.trainerUsername);
        var r = new AssignmentResp(); r.traineeUsername = req.traineeUsername; r.trainerUsername = req.trainerUsername;
        log.info("[{}] RESP 200 AssignmentResp", tx());
        return r;
    }

    @GetMapping("/trainers/{username}/trainees")
    @ApiOperation("List trainees of a trainer")
    public TrainerTraineesResp listTrainees(@PathVariable String username) {
        log.info("[{}] GET /api/trainers/{}/trainees", tx(), username);
        var list = trainerService.getTrainees(username);
        var r = new TrainerTraineesResp(); r.trainerUsername = username; r.trainees = list;
        log.info("[{}] RESP 200 TrainerTraineesResp", tx());
        return r;
    }

    @GetMapping("/trainees/{username}/trainers")
    @ApiOperation("List trainers of a trainee")
    public TraineeTrainersResp listTrainers(@PathVariable String username) {
        log.info("[{}] GET /api/trainees/{}/trainers", tx(), username);
        var list = traineeService.getTrainers(username);
        var r = new TraineeTrainersResp(); r.traineeUsername = username; r.trainers = list;
        log.info("[{}] RESP 200 TraineeTrainersResp", tx());
        return r;
    }

    @GetMapping("/trainees/{username}/trainings")
    @ApiOperation("List trainings by trainee")
    public TraineeTrainingsResp traineeTrainings(@PathVariable String username) {
        log.info("[{}] GET /api/trainees/{}/trainings", tx(), username);
        var list = trainingService.findByTrainee(username).stream().map(t -> t.getName()).collect(Collectors.toList());
        var r = new TraineeTrainingsResp(); r.traineeUsername = username; r.trainingNames = list;
        log.info("[{}] RESP 200 TraineeTrainingsResp", tx());
        return r;
    }

    @GetMapping("/trainers/{username}/trainings")
    @ApiOperation("List trainings by trainer")
    public TrainerTrainingsResp trainerTrainings(@PathVariable String username) {
        log.info("[{}] GET /api/trainers/{}/trainings", tx(), username);
        var list = trainingService.findByTrainer(username).stream().map(t -> t.getName()).collect(Collectors.toList());
        var r = new TrainerTrainingsResp(); r.trainerUsername = username; r.trainingNames = list;
        log.info("[{}] RESP 200 TrainerTrainingsResp", tx());
        return r;
    }

    @PostMapping("/trainings")
    @ApiOperation("Add training session")
    public TrainingAddResp addTraining(@Valid @RequestBody TrainingAddReq req) {
        log.info("[{}] POST /api/trainings {}", tx(), req);
        var t = trainingService.create(req.name, req.traineeUsername, req.trainerUsername, req.date, req.duration, req.typeCode);
        var r = new TrainingAddResp(); r.id = t.getId(); r.name = t.getName();
        log.info("[{}] RESP 200 TrainingAddResp", tx());
        return r;
    }

    @PatchMapping("/trainings/{id}/activate")
    @ApiOperation("Activate training")
    public TrainingActionResp activate(@PathVariable Long id) {
        log.info("[{}] PATCH /api/trainings/{}/activate", tx(), id);
        var status = trainingService.activate(id);
        var r = new TrainingActionResp(); r.id = id; r.status = status;
        log.info("[{}] RESP 200 TrainingActionResp", tx());
        return r;
    }

    @PatchMapping("/trainings/{id}/deactivate")
    @ApiOperation("Deactivate training")
    public TrainingActionResp deactivate(@PathVariable Long id) {
        log.info("[{}] PATCH /api/trainings/{}/deactivate", tx(), id);
        var status = trainingService.deactivate(id);
        var r = new TrainingActionResp(); r.id = id; r.status = status;
        log.info("[{}] RESP 200 TrainingActionResp", tx());
        return r;
    }

    @GetMapping("/training-types")
    @ApiOperation("List training types")
    public List<TrainingTypeResp> listTypes() {
        log.info("[{}] GET /api/training-types", tx());
        var types = trainingService.getTypes();
        var resp = types.stream().map(t -> {
            var r = new TrainingTypeResp(); r.code=t.getCode(); r.description=t.getDescription(); return r;
        }).collect(Collectors.toList());
        log.info("[{}] RESP 200 TrainingTypeResp list", tx());
        return resp;
    }
}