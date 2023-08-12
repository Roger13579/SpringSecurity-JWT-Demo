package com.rogerli.springmall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;
    private Integer orderId;
    @Column(name = "product_id")
    private Integer productId;
    private Integer quantity;
    private Integer amount;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
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
