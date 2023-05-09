package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.model.OrderView;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    List<OrderView> getOrders(OrderQueryParams orderQueryParams);
    Integer countOrder(OrderQueryParams orderQueryParams);

    OrderView getOrderById(Integer orderId);
}
