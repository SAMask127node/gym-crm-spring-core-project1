package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TrainingAddReq")
public class TrainingAddReq {
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String name;
    
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String traineeUsername;
    
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String trainerUsername;
    
    @NotNull  
    @ApiModelProperty(required=true) 
    private LocalDate date;
    
    @NotNull  
    @ApiModelProperty(required=true) 
    private Integer duration;
    
    @NotBlank 
    @ApiModelProperty(required=true) 
    private String typeCode;
}