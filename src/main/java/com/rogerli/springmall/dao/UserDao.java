package com.rogerli.springmall.dao;

import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserByEmail(String email);
}
