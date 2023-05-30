package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User getUserById(Integer userId);

    User register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);
//    User login(UserLoginRequest userLoginRequest);

    User createUser(UserRegisterRequest userRegisterRequest);
}
