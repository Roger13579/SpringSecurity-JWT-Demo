package com.rogerli.springmall.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
public class UserRegisterRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private List<String> authorities;

}
