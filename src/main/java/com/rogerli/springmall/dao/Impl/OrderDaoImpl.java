package com.rogerli.springmall.dao.Impl;

import com.rogerli.springmall.dao.OrderDao;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.entity.OrderItem;
import com.rogerli.springmall.entity.Orders;
import com.rogerli.springmall.model.OrderItemView;
import com.rogerli.springmall.repository.OrderItemJpaDao;
import com.rogerli.springmall.repository.OrderJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private OrderJpaDao orderJpaDao;

    @Autowired
    private OrderItemJpaDao orderItemJpaDao;

    @Override
    public List<Orders> getOrders(OrderQueryParams orderQueryParams) {
        List<Orders> orderList = orderJpaDao.findByUserId(orderQueryParams.getUserId());
        return orderList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalamount) {
        String sql = "INSERT INTO orders (user_id, total_amount, created_date, last_modified_date) " +
                "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalamount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItemView> orderItemList) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItemView orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity",orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public Orders getOrderByOrderId(Integer orderId) {
        List<Orders> ordersList = orderJpaDao.findByOrderId(orderId);
        if (ordersList.size() > 0) {
            return ordersList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        List<OrderItem> orderItemList = orderItemJpaDao.findOrderItemByOrderIdJoinProduct(orderId);
        return orderItemList;
    }

    private String addFilteringSql(String sql, Map<String,Object> map, OrderQueryParams orderQueryParams){
        if (orderQueryParams.getUserId() != null){
            sql = sql + " AND user_id = :userId ";
            map.put("userId",orderQueryParams.getUserId());
        }
        return sql;
    }

}
