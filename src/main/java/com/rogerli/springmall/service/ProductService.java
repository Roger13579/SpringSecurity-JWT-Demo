package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Product createProduct(ProductRequest productRequest);
    Product updateProduct(Product product, ProductRequest productRequest);

    void deleteProductById(Product product);
}
