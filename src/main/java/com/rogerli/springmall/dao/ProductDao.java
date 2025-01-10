package com.rogerli.springmall.dao;

import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductDao {
    Page<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Product createOrUpdateProduct(Product product);

    void updateStock(Integer productId, Integer stock);

    void deleteProductById(Product product);

}
