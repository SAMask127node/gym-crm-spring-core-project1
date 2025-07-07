package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("AssignmentResp")
public class AssignmentResp {
    private String traineeUsername;
    private String trainerUsername;
}