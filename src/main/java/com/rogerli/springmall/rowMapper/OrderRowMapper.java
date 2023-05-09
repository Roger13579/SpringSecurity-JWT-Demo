package com.rogerli.springmall.rowMapper;

import com.rogerli.springmall.entity.Orders;
import com.rogerli.springmall.model.OrderView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<OrderView> {
    @Override
    public OrderView mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderView orderView = new OrderView();
        orderView.setOrderId(rs.getInt("order_id"));
        orderView.setUserId(rs.getInt("user_id"));
        orderView.setTotalAmount(rs.getInt("total_amount"));
        orderView.setCreatedDate(rs.getTimestamp("created_date"));
        orderView.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
        return orderView;
    }
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
