package com.rogerli.springmall.service;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.model.ProductView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> getProducts(ProductQueryParams productQueryParams);

    ProductView getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    Integer countProduct(ProductQueryParams productQueryParams);
}
