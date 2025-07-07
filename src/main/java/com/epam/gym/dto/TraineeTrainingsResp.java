package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TraineeTrainingsResp")
public class TraineeTrainingsResp {
    private String traineeUsername;
    private List<String> trainingNames;
}