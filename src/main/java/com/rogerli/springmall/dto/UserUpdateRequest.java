package com.rogerli.springmall.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserUpdateRequest {

    @NotBlank
    private String password;

}
