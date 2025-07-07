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
@ApiModel("TrainerTrainingsResp")
public class TrainerTrainingsResp {
    private String trainerUsername;
    private List<String> trainingNames;
}