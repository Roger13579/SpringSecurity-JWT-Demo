package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
