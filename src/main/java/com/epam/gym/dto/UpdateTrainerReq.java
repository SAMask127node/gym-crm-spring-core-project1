package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UpdateTrainerReq")
public class UpdateTrainerReq {
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String firstName;
    
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String lastName;
    
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String specialization;
}