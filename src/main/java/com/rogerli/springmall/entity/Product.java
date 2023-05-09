package com.rogerli.springmall.entity;

import com.rogerli.springmall.constant.ProductCategory;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Entity
public class Product {
    @Id
    @Column(name = "product_id")
    private Integer productId;
    private String productName;
    private ProductCategory category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private Date createDate;
    private Date lastModifiedDate;

    @OneToOne(mappedBy = "product")
    private OrderItem orderItem;

}
