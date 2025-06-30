package com.epam.gym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@ApiModel(value="TraineeRegReq")
public class TraineeRegReq {
    @NotBlank @ApiModelProperty(required=true) public String firstName;
    @NotBlank @ApiModelProperty(required=true) public String lastName;
    @ApiModelProperty(required=false) public LocalDate dateOfBirth;
    @ApiModelProperty(required=false) public String address;
}

@ApiModel("TraineeRegResp")
public class TraineeRegResp {
    public String username;
    public String password;
}

@ApiModel("TrainerRegReq")
public class TrainerRegReq {
    @NotBlank public String firstName;
    @NotBlank public String lastName;
    @NotBlank public String specialization;
}
@ApiModel("TrainerRegResp")
public class TrainerRegResp {
    public String username;
    public String password;
}

@ApiModel("LoginReq")
public class LoginReq {
    @NotBlank public String username;
    @NotBlank public String password;
}

// 4) Change Login: PUT /login
@ApiModel("ChangeLoginReq")
public class ChangeLoginReq {
    @NotBlank @ApiModelProperty(required=true) public String oldUsername;
    @NotBlank @ApiModelProperty(required=true) public String newUsername;
}

// 5) Trainee Profile: GET /trainees/{username}
@ApiModel("TraineeProfileResp")
public class TraineeProfileResp {
    public String username;
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public String address;
}

// 6) Update Trainee: PUT /trainees/{username}
@ApiModel("UpdateTraineeReq")
public class UpdateTraineeReq {
    @NotBlank @ApiModelProperty(required=true) public String firstName;
    @NotBlank @ApiModelProperty(required=true) public String lastName;
    @ApiModelProperty(required=false) public LocalDate dateOfBirth;
    @ApiModelProperty(required=false) public String address;
}

// 7) Trainer Profile: GET /trainers/{username}
@ApiModel("TrainerProfileResp")
public class TrainerProfileResp {
    public String username;
    public String firstName;
    public String lastName;
    public String specialization;
}

// 8) Update Trainer: PUT /trainers/{username}
@ApiModel("UpdateTrainerReq")
public class UpdateTrainerReq {
    @NotBlank @ApiModelProperty(required=true) public String firstName;
    @NotBlank @ApiModelProperty(required=true) public String lastName;
    @NotBlank @ApiModelProperty(required=true) public String specialization;
}

// 9) Assign Trainer to Trainee: POST /assignments
@ApiModel("AssignmentReq")
public class AssignmentReq {
    @NotBlank @ApiModelProperty(required=true) public String traineeUsername;
    @NotBlank @ApiModelProperty(required=true) public String trainerUsername;
}

@ApiModel("AssignmentResp")
public class AssignmentResp {
    public String traineeUsername;
    public String trainerUsername;
}

// 10) List Trainees of a Trainer: GET /trainers/{username}/trainees
@ApiModel("TrainerTraineesResp")
public class TrainerTraineesResp {
    public String trainerUsername;
    public List<String> trainees;
}

// 11) List Trainers of a Trainee: GET /trainees/{username}/trainers
@ApiModel("TraineeTrainersResp")
public class TraineeTrainersResp {
    public String traineeUsername;
    public List<String> trainers;
}

// 12) List Trainings by Trainee: GET /trainees/{username}/trainings
@ApiModel("TraineeTrainingsResp")
public class TraineeTrainingsResp {
    public String traineeUsername;
    public List<String> trainingNames;
}

// 13) List Trainings by Trainer: GET /trainers/{username}/trainings
@ApiModel("TrainerTrainingsResp")
public class TrainerTrainingsResp {
    public String trainerUsername;
    public List<String> trainingNames;
}

// 14) Add Training: POST /trainings
@ApiModel("TrainingAddReq")
public class TrainingAddReq {
    @NotBlank @ApiModelProperty(required=true) public String name;
    @NotBlank @ApiModelProperty(required=true) public String traineeUsername;
    @NotBlank @ApiModelProperty(required=true) public String trainerUsername;
    @NotNull  @ApiModelProperty(required=true) public LocalDate date;
    @NotNull  @ApiModelProperty(required=true) public Integer duration;
    @NotBlank @ApiModelProperty(required=true) public String typeCode;
}

@ApiModel("TrainingAddResp")
public class TrainingAddResp {
    public Long id;
    public String name;
}

// 15) Activate Training: PATCH /trainings/{id}/activate
@ApiModel("TrainingActionResp")
public class TrainingActionResp {
    public Long id;
    public String status;
}

// 16) Deactivate Training: PATCH /trainings/{id}/deactivate
// Uses TrainingActionResp

// 17) List Training Types: GET /training-types
@ApiModel("TrainingTypeResp")
public class TrainingTypeResp {
    public String code;
    public String description;
}