package com.rogerli.springmall.model;

import com.rogerli.springmall.entity.OrderItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderView {
    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createdDate;
    private Date lastModifiedDate;
    private List<OrderItemView> orderItemViewList;
}
