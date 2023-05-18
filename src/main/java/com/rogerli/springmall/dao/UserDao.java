package com.rogerli.springmall.dao;

import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.UserView;

public interface UserDao {

    User getUserById(Integer userId);

    User createUser(User user);

    User getUserByEmail(String email);
}
