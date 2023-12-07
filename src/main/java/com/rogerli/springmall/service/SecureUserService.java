package com.rogerli.springmall.service;

import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.SecureUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecureUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    private static final Logger logger = LogManager.getLogger(SecureUserService.class);

    /**
     * Loads a user by the given username.
     *
     * @param username The username to load the user by.
     * @return The loaded UserDetails object.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDao.getUserByEmail(username);
            if (user == null) {
                logger.error("該帳號不存在");
                throw new UsernameNotFoundException("該帳號不存在");
            }
            return new SecureUser(user);
        }catch (Exception e){
            logger.error("Error loading user by username: " + username, e);
            throw new UsernameNotFoundException(username);
        }
    }

}