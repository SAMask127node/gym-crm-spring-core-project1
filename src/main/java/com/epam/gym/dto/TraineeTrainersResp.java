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
@ApiModel("TraineeTrainersResp")
public class TraineeTrainersResp {
    private String traineeUsername;
    private List<String> trainers;
}