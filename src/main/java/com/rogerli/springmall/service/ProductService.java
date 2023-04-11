package com.rogerli.springmall.service;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
