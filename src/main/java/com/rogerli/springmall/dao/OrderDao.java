package com.rogerli.springmall.dao;

import com.rogerli.springmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalamount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);
}
