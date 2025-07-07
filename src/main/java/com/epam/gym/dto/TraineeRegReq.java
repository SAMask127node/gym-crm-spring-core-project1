package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TraineeRegReq")
public class TraineeRegReq {
    @NotBlank 
    @ApiModelProperty(required = true) 
    private String firstName;
    
    @NotBlank 
    @ApiModelProperty(required = true) 
    private String lastName;
    
    @ApiModelProperty(required = false) 
    private LocalDate dateOfBirth;
    
    @ApiModelProperty(required = false) 
    private String address;
}