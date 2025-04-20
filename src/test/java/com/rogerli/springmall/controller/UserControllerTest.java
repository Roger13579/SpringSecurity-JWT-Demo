package com.rogerli.springmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.repository.RoleJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the UserController REST controller.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleJpaRepository roleJpaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Test user registration.
     * Verifies that the endpoint returns the login view after successful registration
     * and that the password is properly encrypted in the database.
     */
    @Test
    @Transactional
    @DisplayName("Should register user and return login view")
    public void shouldRegisterUserAndReturnLoginView() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test1@gmail.com")
                .param("password", "123")
                .param("authorities", "NORMAL");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        // Verify that the password is encrypted in the database
        User user = userDao.getUserByEmail("test1@gmail.com");
        assertNotEquals("123", user.getPassword());
    }

    /**
     * Test user registration with invalid email format.
     * Note: The current implementation accepts invalid email formats, which is a potential issue.
     */
    @Test
    @DisplayName("Should accept registration with invalid email format")
    public void shouldAcceptRegistrationWithInvalidEmailFormat() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "3gd8e7q34l9") // Invalid email format
                .param("password", "123")
                .param("authorities", "NORMAL");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * Test user registration with an email that already exists.
     * Verifies that the endpoint returns the register view with an error message when attempting to register with an email that is already in use.
     */
    @Test
    @Transactional
    @DisplayName("Should return register view with error when registering with email that already exists")
    public void shouldReturnRegisterViewWithErrorWhenRegisteringWithExistingEmail() throws Exception {
        // Register a user first
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test2@gmail.com")
                .param("password", "123")
                .param("authorities", "NORMAL");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        // Try to register with the same email again
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"));
    }

    /**
     * Test user login with valid credentials.
     * Note: This test is skipped because it requires more complex setup with Spring Security.
     */
    @Test
    @Transactional
    @DisplayName("Should login user and return user JWT token")
    public void shouldLoginUser() throws Exception {
        register();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "abc@gmail.com") // Invalid email format
                .param("password", "123");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(notNullValue())));
    }

    /**
     * Test user login with wrong password.
     * Note: This test is skipped because it requires more complex setup with Spring Security.
     */
    @Test
    @Transactional
    @DisplayName("Should return 400 when logging in with wrong password")
    public void shouldReturn400WhenLoggingInWithWrongPassword() throws Exception {
        register();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "abc@gmail.com") // Invalid email format
                .param("password", "321");
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

    /**
     * Test user login with an invalid email format.
     * Note: This test is skipped because it requires a more complex setup with Spring Security.
     */
    @Test
    @DisplayName("Should return 400 when logging in with invalid email format")
    public void shouldReturn400WhenLoggingInWithInvalidEmailFormat() throws Exception {
        register();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "InvalidEmailFormat") // Invalid email format
                .param("password", "321");
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

    /**
     * Test user login with non-existent email.
     * Note: This test is skipped because it requires a more complex setup with Spring Security.
     */
    @Test
    @DisplayName("Should return 400 when logging in with non-existent email")
    public void shouldReturn400WhenLoggingInWithNonExistentEmail() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "NonExistentEmail@gmail.com") // Invalid email format
                .param("password", "321");
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

    /**
     * Helper method to register a user for testing purposes.
     *
     * @throws Exception if an error occurs during registration
     */
    private void register() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "abc@gmail.com")
                .param("password", "123")
                .param("authorities", "NORMAL");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

}
