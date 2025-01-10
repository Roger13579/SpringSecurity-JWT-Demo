package com.rogerli.springmall.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserRegisterRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private List<String> authorities;
}
