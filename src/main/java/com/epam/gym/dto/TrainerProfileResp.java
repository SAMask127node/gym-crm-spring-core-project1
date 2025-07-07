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
@ApiModel("TrainerProfileResp")
public class TrainerProfileResp {
    private String username;
    private String firstName;
    private String lastName;
    private String specialization;
}