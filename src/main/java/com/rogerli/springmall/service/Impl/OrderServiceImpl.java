package com.rogerli.springmall.service.Impl;

import com.rogerli.springmall.dao.OrderDao;
import com.rogerli.springmall.dao.ProductDao;
import com.rogerli.springmall.dto.BuyItem;
import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.model.OrderItem;
import com.rogerli.springmall.model.Product;
import com.rogerli.springmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            int amount = buyItem.getQuantity()*product.getPrice();
            totalAmount += amount;

            // 轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItem(orderId, orderItemList);

        return orderId;
    }
}
