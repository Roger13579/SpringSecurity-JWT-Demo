package com.rogerli.springmall.rowMapper;

import com.rogerli.springmall.entity.OrderItem;
import com.rogerli.springmall.model.OrderItemView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItemView> {
    @Override
    public OrderItemView mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItemView orderItem = new OrderItemView();
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));
        orderItem.setProductName(rs.getString("product_name"));
        orderItem.setImageUrl(rs.getString("image_url"));
        return orderItem;
    }

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
