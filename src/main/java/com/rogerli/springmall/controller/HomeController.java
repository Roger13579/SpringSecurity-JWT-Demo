package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.dto.UserUpdateRequest;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.UserIdentity;
import com.rogerli.springmall.service.Impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserIdentity userIdentity;
    private final static Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/index")
    public String homePage(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication){
        System.out.println(authentication.getAuthorities());
        if (userService.isLogin()){
            UserUpdateRequest user = new UserUpdateRequest();
            UserRegisterRequest register = new UserRegisterRequest();
            model.addAttribute("user", user);
            model.addAttribute("register", register);
            return "user";
        }else {
            return "login";
        }
    }
    @PostMapping("/update")
    public String updatePassword(@Valid UserUpdateRequest userUpdateRequest, Authentication authentication, Model model){
        UserUpdateRequest user = new UserUpdateRequest();
        model.addAttribute("user", user);
        try {
            userService.update(userUpdateRequest, authentication);
        } catch (Exception e) {
            log.error("",e);
            model.addAttribute("error",
                    "與舊密碼相同，請重新輸入");
            return "user";
        }
        model.addAttribute("success","修改成功");
        return "user";
    }
    @GetMapping("/register")
    public String register(Model model){
        UserRegisterRequest user = new UserRegisterRequest();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/users/register")
    public String register(@Valid UserRegisterRequest userRegisterRequest, @RequestParam("authorities") String[] authorities, Model model){
        userRegisterRequest.setAuthorities(Arrays.asList(authorities));
        System.out.println(userRegisterRequest);
        try {
            userService.createUser(userRegisterRequest);
        } catch (Exception e) {
            UserRegisterRequest user = new UserRegisterRequest();
            model.addAttribute("user", user);
            model.addAttribute("error",
                    "此帳號已註冊 " + userRegisterRequest.getEmail());
            return "register";
        }
        return "login";
    }
}
