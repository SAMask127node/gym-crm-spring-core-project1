package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request DTO for trainee profile")
public class TraineeProfileReq {
    @NotBlank
    private String username;
}