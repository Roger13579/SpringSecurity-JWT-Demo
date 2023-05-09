package com.rogerli.springmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {
    @Id
    private Integer orderItemId;
    private Integer orderId;
    @Column(name = "product_id",insertable = false,updatable = false)
    private Integer productId;
    private Integer quantity;
    private Integer amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    public OrderItem(Integer orderItemId, Integer orderId, Integer productId, Integer quantity, Integer amount, String productame, String imageUrl) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.product = new Product();
        product.setProductName(productame);
        product.setImageUrl(imageUrl);
    }
}
