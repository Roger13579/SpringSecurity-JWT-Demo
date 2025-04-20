package com.rogerli.springmall.mapper;

import com.rogerli.springmall.entity.Orders;
import com.rogerli.springmall.model.OrderView;

public class OrderMapper {
    
    public static OrderView mapOrder(Orders orderEntity){
        OrderView orderView = new OrderView();
        orderView.setOrderId(orderEntity.getOrderId());
        orderView.setUserId(orderEntity.getUserId());
        orderView.setTotalAmount(orderEntity.getTotalAmount());
        orderView.setCreatedDate(orderEntity.getCreatedDate());
        orderView.setLastModifiedDate(orderEntity.getLastModifiedDate());
        return orderView;
    }
}