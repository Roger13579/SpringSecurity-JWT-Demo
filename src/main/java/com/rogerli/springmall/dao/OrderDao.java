package com.rogerli.springmall.dao;

import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.entity.OrderItem;
import com.rogerli.springmall.entity.Orders;
import com.rogerli.springmall.model.OrderItemView;

import java.util.List;

public interface OrderDao {
    List<Orders> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Orders orders);

    void createOrderItem(List<OrderItem> orderItemViewList);

    Orders getOrderByOrderId(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
