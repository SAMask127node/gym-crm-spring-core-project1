package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("AssignmentReq")
public class AssignmentReq {
    @NotBlank 
    @ApiModelProperty(required=true) 
    private final String traineeUsername;
    
    @NotBlank 
    @ApiModelProperty(required=true) 
    private final String trainerUsername;
}