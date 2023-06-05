package com.rogerli.springmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogerli.springmall.constant.UserAuthority;
import com.rogerli.springmall.dto.UserLoginRequest;
import com.rogerli.springmall.entity.Roles;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.repository.RoleJpaRepository;
import com.rogerli.springmall.repository.UserJpaDao;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    protected final ObjectMapper mapper = new ObjectMapper();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String USER_PASSWORD = "123456";

    @BeforeEach
    public void initHttpHeaders(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
    }

    protected User createUser_Admin() {
        User user = new User();
        Set<Roles> set = new HashSet<>();
        Roles admin = roleJpaRepository.findByRoleName(UserAuthority.ADMIN);
        Date now = new Date();
        set.add(admin);
        user.setEmail("roger@gmail.com");
        user.setPassword(passwordEncoder.encode(USER_PASSWORD));
        user.setAuthorities(set);
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        return userJpaDao.save(user);
    }

    protected void login(User user) throws Exception {
        UserLoginRequest authReq = new UserLoginRequest();
        authReq.setEmail(user.getEmail());
        authReq.setPassword(USER_PASSWORD);
        RequestBuilder authRequestBuilder = MockMvcRequestBuilders
                .post("/users/auth")
                .headers(httpHeaders)
                .content(mapper.writeValueAsString(authReq));

        MvcResult result = mockMvc.perform(authRequestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        JSONObject tokenRes = new JSONObject(result.getResponse().getContentAsString());
        String accessToken = tokenRes.getString("token");
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }

}
