package com.rogerli.springmall.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {

    @NotBlank
    private String password;

}
