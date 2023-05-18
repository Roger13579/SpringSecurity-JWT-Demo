package com.rogerli.springmall.service.Impl;

import com.rogerli.springmall.dao.OrderDao;
import com.rogerli.springmall.dao.ProductDao;
import com.rogerli.springmall.dao.UserDao;
import com.rogerli.springmall.dto.BuyItem;
import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.entity.OrderItem;
import com.rogerli.springmall.entity.Orders;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.entity.User;
import com.rogerli.springmall.model.OrderView;
import com.rogerli.springmall.model.OrderItemView;
import com.rogerli.springmall.model.UserView;
import com.rogerli.springmall.rowMapper.OrderItemRowMapper;
import com.rogerli.springmall.rowMapper.OrderRowMapper;
import com.rogerli.springmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 檢查user是否存在
        User user = userDao.getUserById(userId);
        if (user == null){
            log.warn("該 userid {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查product是否存在,數量是否足夠
            if (product == null){
                log.warn("商品不存在:{}",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存不足，剩餘庫存:{}",buyItem.getProductId(),product.getStock());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
        Orders orders = new Orders();
        Date now = new Date();
        orders.setUserId(userId);
        orders.setTotalAmount(totalAmount);
        orders.setCreatedDate(now);
        orders.setLastModifiedDate(now);
        Integer orderId = orderDao.createOrder(orders);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderId);
        }
        orderDao.createOrderItem(orderItemList);

        return orderId;
    }

    @Override
    public List<OrderView> getOrders(OrderQueryParams orderQueryParams) {
        List<Orders> orderList = orderDao.getOrders(orderQueryParams);
        List<OrderView> orderViewList = orderList.stream().map(OrderRowMapper::mapOrder).collect(Collectors.toList());
        for (OrderView orderView : orderViewList){
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderView.getOrderId());
            List<OrderItemView> orderItemViews = orderItemList.stream().map(OrderItemRowMapper::mapOrderItem).collect(Collectors.toList());
            orderView.setOrderItemViewList(orderItemViews);
        }
        return orderViewList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        List<Orders> orderList = orderDao.getOrders(orderQueryParams);
        return orderList.size();
    }

    @Override
    public OrderView getOrderById(Integer orderId) {
        Orders order = orderDao.getOrderByOrderId(orderId);
        OrderView orderView = OrderRowMapper.mapOrder(order);
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        List<OrderItemView> orderItemViews = orderItemList.stream().map(OrderItemRowMapper::mapOrderItem).collect(Collectors.toList());
        orderView.setOrderItemViewList(orderItemViews);
        return orderView;
    }
}
