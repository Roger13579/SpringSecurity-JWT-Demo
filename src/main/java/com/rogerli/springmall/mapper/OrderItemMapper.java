package com.rogerli.springmall.mapper;

import com.rogerli.springmall.entity.OrderItem;
import com.rogerli.springmall.model.OrderItemView;

public class OrderItemMapper {
    
    public static OrderItemView mapOrderItem(OrderItem orderItem){
        OrderItemView orderItemView = new OrderItemView();
        orderItemView.setOrderItemId(orderItem.getOrderItemId());
        orderItemView.setOrderId(orderItem.getOrderId());
        orderItemView.setProductId(orderItem.getProductId());
        orderItemView.setQuantity(orderItem.getQuantity());
        orderItemView.setAmount(orderItem.getAmount());
        orderItemView.setProductName(orderItem.getProduct().getProductName());
        orderItemView.setImageUrl(orderItem.getProduct().getImageUrl());
        return orderItemView;
    }
}