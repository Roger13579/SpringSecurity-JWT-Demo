package com.rogerli.springmall.dao;

import com.rogerli.springmall.entity.User;

public interface UserDao {

    User getUserById(Integer userId);

    User createUser(User user);

    User getUserByEmail(String email);
}
