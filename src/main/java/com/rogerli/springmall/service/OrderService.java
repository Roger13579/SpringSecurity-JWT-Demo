package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.dto.UpdateOrderRequest;
import com.rogerli.springmall.model.OrderView;

import java.util.List;

public interface OrderService {

    Integer createOrder(CreateOrderRequest createOrderRequest, boolean iscart);

    void shoppingCartToOrder(UpdateOrderRequest updateOrderRequest, boolean iscart);

    List<OrderView> getOrders(OrderQueryParams orderQueryParams);
    Integer countOrder(OrderQueryParams orderQueryParams);

    OrderView getOrderById(Integer orderId);

    void deleteOrderById(Integer orderId);
}
