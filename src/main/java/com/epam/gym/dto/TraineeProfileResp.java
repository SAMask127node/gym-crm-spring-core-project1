package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TraineeProfileResp")
public class TraineeProfileResp {
    @ApiModelProperty(required = true)
    private String firstName;

    @ApiModelProperty(required = true)
    private String lastName;

    @ApiModelProperty(required = true)
    private String dateOfBirth;

    @ApiModelProperty(required = true)
    private String address;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private Boolean isActive;

    @ApiModelProperty(required = true)
    private List<TrainerResponse> trainers;
}