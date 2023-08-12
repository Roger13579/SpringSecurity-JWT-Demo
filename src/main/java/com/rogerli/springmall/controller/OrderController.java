package com.rogerli.springmall.controller;

import com.rogerli.springmall.dto.BuyItem;
import com.rogerli.springmall.dto.CreateOrderRequest;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.dto.UpdateOrderRequest;
import com.rogerli.springmall.model.OrderView;
import com.rogerli.springmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public String createOrder(UpdateOrderRequest updateOrderRequest, Model model){
        var iscart = false;
        orderService.shoppingCartToOrder(updateOrderRequest, iscart);
        return getOrders(model);
    }

    @GetMapping("/orders")
    public String getOrders(Model model){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setIscart(false);
        // 取得orderlist
        List<OrderView> orderViewList = orderService.getOrders(orderQueryParams);
        // 取得訂單總數
        Integer count = orderService.countOrder(orderQueryParams);
        model.addAttribute("count", count);
        model.addAttribute("orderViewList", orderViewList);
        return "myorders";
    }
    @GetMapping("/orders/cart")
    public String shoppingCart(Model model){
        var orderQueryParams = new OrderQueryParams();
        orderQueryParams.setIscart(true);
        // 取得orderlist
        List<OrderView> orderViewList = orderService.getOrders(orderQueryParams);
        model.addAttribute("orderViewList", orderViewList);
        return "shoppingcart";
    }
    @PostMapping("/orders/cart")
    public String AddToShoppingCart(@Valid BuyItem buyItem, Model model){
        List<BuyItem> buyItemList = new ArrayList<>();
        buyItemList.add(buyItem);
        var createOrderRequest = new CreateOrderRequest();
        var iscart = true;
        createOrderRequest.setBuyItemList(buyItemList);
        orderService.createOrder(createOrderRequest, iscart);
        return shoppingCart(model);
    }

    @GetMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Integer orderId, Model model){
        orderService.deleteOrderById(orderId);
        return shoppingCart(model);
    }
}
