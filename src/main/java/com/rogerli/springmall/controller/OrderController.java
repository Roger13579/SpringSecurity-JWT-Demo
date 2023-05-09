package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.model.OrderView;
import com.rogerli.springmall.service.OrderService;
import com.rogerli.springmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<OrderView> createOrder(@PathVariable Integer userId,
                                                 @Valid @RequestBody CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        OrderView orderView = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderView);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<OrderView>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // 取得orderlist
        List<OrderView> orderViewList = orderService.getOrders(orderQueryParams);

        // 取得訂單總數
        Integer count = orderService.countOrder(orderQueryParams);
        Page<OrderView> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderViewList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
