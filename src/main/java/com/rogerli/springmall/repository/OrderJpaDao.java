package com.rogerli.springmall.repository;

import com.rogerli.springmall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaDao extends JpaRepository<Orders,Integer> {

    List<Orders> findByUserIdAndIscartOrderByCreatedDateDesc(Integer userId, boolean iscart);

    List<Orders> findByOrderId(Integer orderId);



}
