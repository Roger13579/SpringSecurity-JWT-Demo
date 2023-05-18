package com.rogerli.springmall.dao;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.model.ProductView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductDao {
    Page<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Product createOrUpdateProduct(Product product);

    void updateStock(Integer productId, Integer stock);

    void deleteProductById(Integer productId);

}
