package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TrainerRegReq")
public class TrainerRegReq {
    @NotBlank 
    private String firstName;
    
    @NotBlank 
    private String lastName;
    
    @NotBlank 
    private String specialization;
}