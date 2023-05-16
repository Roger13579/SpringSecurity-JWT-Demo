package com.rogerli.springmall.repository;

import com.rogerli.springmall.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemJpaDao extends JpaRepository<OrderItem,Long> {

    @Query("SELECT new OrderItem(oi.orderItemId, oi.orderId, oi.productId,oi.amount, " +
            "oi.quantity, p.productName, p.imageUrl) " +
            "FROM OrderItem as oi " +
            "LEFT JOIN Product as p ON oi.productId = p.productId " +
            "WHERE oi.orderId = :orderId ")
    List<OrderItem> findOrderItemByOrderIdJoinProduct(@Param("orderId") int orderId);



}
