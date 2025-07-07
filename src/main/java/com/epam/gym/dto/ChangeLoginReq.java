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
@ApiModel("ChangeLoginReq")
public class ChangeLoginReq {
    @NotBlank 
    @ApiModelProperty(required = true) 
    private String oldUsername;
    
    @NotBlank 
    @ApiModelProperty(required = true) 
    private String newUsername;
}