package com.rogerli.springmall.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
