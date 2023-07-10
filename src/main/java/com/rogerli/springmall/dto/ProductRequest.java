package com.rogerli.springmall.dto;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProductRequest {
    @NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    private String description;

    public ProductRequest(Product product) {
        this.productName = product.getProductName();
        this.category = ProductCategory.valueOf(product.getCategory());
        this.imageUrl = product.getImageUrl();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.description = product.getDescription();
    }
}
