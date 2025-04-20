package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.dto.UserUpdateRequest;
import com.rogerli.springmall.service.JWTService;
import com.rogerli.springmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private HomeController homeController;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users/auth")
    public ResponseEntity<Map<String, String>> issueToken(@Valid UserLoginRequest request) {
        String token = jwtService.generateToken(request);
        Map<String, String> response = Collections.singletonMap("token", token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/users/parse")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
    @PostMapping("/users/update")
    public String updatePassword(@Valid UserUpdateRequest userUpdateRequest, Authentication authentication, Model model){
        UserUpdateRequest user = new UserUpdateRequest();
        model.addAttribute("user", user);
        try {
            userService.update(userUpdateRequest, authentication);
        } catch (Exception e) {
            log.error("",e);
            model.addAttribute("error",
                    "與舊密碼相同，請重新輸入");
            return homeController.userPage(model);
        }
        model.addAttribute("success","修改成功");
        return homeController.userPage(model);
    }
}
