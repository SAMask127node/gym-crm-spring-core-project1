package com.epam.gym.controller;

import com.epam.gym.dto.*;
import com.epam.gym.domain.Training;
import com.epam.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private String tx() { return MDC.get("transactionId"); }

    @PostMapping
    public TrainingAddResp create(@Valid @RequestBody TrainingAddReq request) {
        var training = trainingService.create(request.getName(), request.getTraineeUsername(), request.getTrainerUsername(), request.getDate(), request.getDuration(), request.getTypeCode());
        return TrainingAddResp.builder()
                .id(training.getId())
                .name(training.getName())
                .build();
    }

    @PatchMapping("/{id}/activate")
    public TrainingActionResp activate(@PathVariable Long id) {
        String status = trainingService.activate(id);
        return TrainingActionResp.builder().id(id).status(status).build();
    }

    @PatchMapping("/{id}/deactivate")
    public TrainingActionResp deactivate(@PathVariable Long id) {
        String status = trainingService.deactivate(id);
        return TrainingActionResp.builder().id(id).status(status).build();
    }

    @GetMapping("/types")
    public List<TrainingTypeResp> listTypes() {
        return trainingService.getTypes().stream().map(t ->
                TrainingTypeResp.builder().code(t.getCode()).description(t.getDescription()).build()
        ).collect(Collectors.toList());
    }
}