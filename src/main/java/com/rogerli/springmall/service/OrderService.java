package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
