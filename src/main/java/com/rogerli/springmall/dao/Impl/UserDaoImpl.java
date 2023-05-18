package com.rogerli.springmall.dao.Impl;

import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.UserView;
import com.rogerli.springmall.repository.UserJpaDao;
import com.rogerli.springmall.rowMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
