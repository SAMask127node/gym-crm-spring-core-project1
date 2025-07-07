package com.epam.gym.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("LoginReq")
public class LoginReq {
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
}