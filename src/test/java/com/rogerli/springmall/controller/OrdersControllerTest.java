package com.rogerli.springmall.controller;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderController} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrdersControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test retrieving all orders.
     * Verifies that the endpoint returns the correct view name and model attributes.
     */
    @Test
    @DisplayName("Should return all orders with correct view and model attributes")
    public void shouldReturnAllOrders() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders")
                .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("myorders"))
                .andExpect(model().attributeExists("orderViewList"))
                .andExpect(model().attributeExists("count"));
    }

    /**
     * Test retrieving the shopping cart.
     * Verifies that the endpoint returns the correct view name and model attributes.
     */
    @Test
    @DisplayName("Should return shopping cart with correct view and model attributes")
    public void shouldReturnShoppingCart() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/cart")
                .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("shoppingcart"))
                .andExpect(model().attributeExists("orderViewList"));
    }

    /**
     * Test adding an item to the shopping cart.
     * Verifies that the endpoint returns the correct view name and model attributes.
     */
    @Test
    @Transactional
    @DisplayName("Should add item to shopping cart and return correct view")
    public void shouldAddItemToShoppingCart() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/cart")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", "1") // Valid product ID
                .param("quantity", "1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("shoppingcart"))
                .andExpect(model().attributeExists("orderViewList"));
    }

    /**
     * Test adding an item to the shopping cart with invalid product ID.
     * Verifies that the endpoint returns a 4xx error.
     */
    @Test
    @Transactional
    @DisplayName("Should return error when adding non-existent product to cart")
    public void shouldReturnErrorWhenAddingNonExistentProductToCart() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/cart")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", "999") // Non-existent product ID
                .param("quantity", "1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test adding an item to the shopping cart with insufficient stock.
     * Verifies that the endpoint returns a 4xx error.
     */
    @Test
    @Transactional
    @DisplayName("Should return error when adding item with insufficient stock")
    public void shouldReturnErrorWhenAddingItemWithInsufficientStock() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/cart")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", "1")
                .param("quantity", "10000"); // Very large quantity

        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test creating an order from shopping cart.
     * Uses the second order ID from data.sql to test the conversion from shopping cart to order.
     */
    @Test
    @Transactional
    @DisplayName("Should create order from shopping cart")
    public void shouldCreateOrderFromShoppingCart() throws Exception {
        // Use the second order from data.sql, which has only one order item
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("orderId", "2")
                .param("productId", "4")
                .param("quantity", "1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("myorders"))
                .andExpect(model().attributeExists("orderViewList"))
                .andExpect(model().attributeExists("count"));
    }

    /**
     * Test deleting an order.
     * Uses the second order ID from data.sql to test the deletion of an order.
     */
    @Test
    @Transactional
    @DisplayName("Should delete order and return to shopping cart")
    public void shouldDeleteOrder() throws Exception {
        // Use the second order from data.sql
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/{orderId}/delete", 2)
                .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("shoppingcart"))
                .andExpect(model().attributeExists("orderViewList"));
    }
}
