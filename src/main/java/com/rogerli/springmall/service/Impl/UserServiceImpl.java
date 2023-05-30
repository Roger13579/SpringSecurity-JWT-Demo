package com.rogerli.springmall.service.Impl;

import com.rogerli.springmall.constant.UserAuthority;
import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.entity.Roles;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.SecureUser;
import com.rogerli.springmall.repository.RoleJpaRepository;
import com.rogerli.springmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService{

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User register(UserRegisterRequest userRegisterRequest) {
        User checkUser = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (checkUser != null) {
            log.error("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        Date now = new Date();
        user.setEmail(userRegisterRequest.getEmail());
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        //使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        user.setPassword(hashedPassword);

        return userDao.createUser(user);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        //檢查user是否存在
        if (user == null) {
            log.error("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5比較密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //比較密碼
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.error("該登入 email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
//    @Override
//    public UserDetails login(UserLoginRequest userLoginRequest) {
//        UserDetails userDetails = loadUserByUsername(userLoginRequest.getEmail());
//        //檢查user是否存在
//        if (userDetails.getUsername() == null) {
//            log.error("該 email {} 尚未註冊", userLoginRequest.getEmail());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//        //使用MD5比較密碼的雜湊值
//        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
//
//        //比較密碼
//        if (userDetails.getPassword().equals(hashedPassword)) {
//            return userDetails;
//        } else {
//            log.error("該登入 email {} 的密碼不正確", userLoginRequest.getEmail());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }

    @Override
    public User createUser(UserRegisterRequest userRegisterRequest){
        User checkUser = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (checkUser != null) {
            log.error("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        Date now = new Date();
        Set<Roles> roles = new HashSet<>();
        user.setEmail(userRegisterRequest.getEmail());
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);

        String encryptPassword = new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword());
        user.setPassword(encryptPassword);
        userRegisterRequest.getAuthorities().forEach(
                role ->{
                    switch (role){
                        case "admin":
                            Roles admin = roleJpaRepository.findByRoleName(UserAuthority.ADMIN);
                            roles.add(admin);
                            break;
                        case "normal":
                            Roles normal = roleJpaRepository.findByRoleName(UserAuthority.NORMAL);
                            roles.add(normal);
                            break;
                        default:
                            break;
                    }
                }
        );
        user.setAuthorities(roles);
        return userDao.createUser(user);
    }
}
