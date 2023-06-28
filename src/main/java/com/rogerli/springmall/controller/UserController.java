package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.dto.UserRegisterRequest;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.service.JWTService;
import com.rogerli.springmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        User user = userService.createUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users/auth")
    public ResponseEntity<Map<String, String>> issueToken(@Valid @RequestBody UserLoginRequest request) {
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

}
