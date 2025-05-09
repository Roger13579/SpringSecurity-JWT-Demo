package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.dto.UserUpdateRequest;
import com.rogerli.springmall.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    User getUserById(Integer userId);

    User register(UserRegisterRequest userRegisterRequest);
    User update(UserUpdateRequest userUpdateRequest, Authentication authentication);

    User login(UserLoginRequest userLoginRequest);

    User createUser(UserRegisterRequest userRegisterRequest);
}
