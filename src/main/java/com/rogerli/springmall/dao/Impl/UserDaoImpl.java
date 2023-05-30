package com.rogerli.springmall.dao.Impl;

import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.repository.UserJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserJpaDao userJpaDao;

    @Override
    public User getUserById(Integer userId) {
        return userJpaDao.findByUserId(userId);
    }

    @Override
    public User createUser(User user) {
        return userJpaDao.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userJpaDao.findByEmail(email);
    }
}
