package com.rogerli.springmall.service;

import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.SecureUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecureUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDao.getUserByEmail(username);
            return new SecureUser(user);
        }catch (Exception e){
            throw new UsernameNotFoundException(username);
        }
    }
}
