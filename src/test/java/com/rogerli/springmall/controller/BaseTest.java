package com.rogerli.springmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogerli.springmall.constant.UserAuthority;
import com.rogerli.springmall.entity.Roles;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.repository.RoleJpaRepository;
import com.rogerli.springmall.repository.UserJpaDao;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleJpaRepository roleJpaRepository;
    @Autowired
    private UserJpaDao userJpaDao;
    protected HttpHeaders httpHeaders;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String USER_PASSWORD = "123456";

    protected User createUser_Admin() {
        // Check if user already exists
        User existingUser = userJpaDao.findByEmail("roger@gmail.com");
        if (existingUser != null) {
            return existingUser;
        }

        // Create new user if it doesn't exist
        User user = new User();
        Set<Roles> set = new HashSet<>();
        Roles admin = roleJpaRepository.findByRoleName(UserAuthority.ADMIN);
        Roles normal = roleJpaRepository.findByRoleName(UserAuthority.NORMAL);
        Date now = new Date();
        set.add(admin);
        set.add(normal);
        user.setEmail("roger@gmail.com");
        user.setPassword(passwordEncoder.encode(USER_PASSWORD));
        user.setAuthorities(set);
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        return userJpaDao.save(user);
    }

    @BeforeEach
    protected void login() throws Exception {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        User userAdmin = createUser_Admin();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", userAdmin.getEmail()) // Invalid email format
                .param("password", USER_PASSWORD);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        JSONObject tokenRes = new JSONObject(result.getResponse().getContentAsString());
        String accessToken = tokenRes.getString("token");
        log.info("accessToken:{}", accessToken);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }

}
