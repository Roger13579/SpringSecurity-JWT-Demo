package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.UserIdentity;
import com.rogerli.springmall.service.Impl.UserServiceImpl;
import com.rogerli.springmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserIdentity userIdentity;

    @GetMapping("/index")
    public String homePage(Model model){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model){
        UserRegisterRequest user = new UserRegisterRequest();
        model.addAttribute("user", user);
        return "register";
    }
}
