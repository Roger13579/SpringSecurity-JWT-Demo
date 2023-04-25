package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.BuyItem;
import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @Valid @RequestBody CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }
}
