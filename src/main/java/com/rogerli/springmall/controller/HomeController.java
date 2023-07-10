package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.ProductRequest;
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
    @GetMapping("/register")
    public String register(Model model){
        UserRegisterRequest user = new UserRegisterRequest();
        model.addAttribute("user", user);
        return "register";
    }
    @GetMapping("/addproduct")
    public String addProduct(Model model){
        ProductRequest productRequest = new ProductRequest();
        model.addAttribute("product", productRequest);
        return "addproduct";
    }
}
