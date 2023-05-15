package com.rogerli.springmall.model;

import com.rogerli.springmall.constant.ProductCategory;
import lombok.Data;

import java.util.Date;

@Data
public class ProductView {

    private Integer productId;
    private String productName;
    private ProductCategory category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private Date createdDate;
    private Date lastModifiedDate;

}
