package com.rogerli.springmall.service.Impl;

import com.rogerli.springmall.dao.ProductDao;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.model.ProductView;
import com.rogerli.springmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Page<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setCategory(productRequest.getCategory().name());
        product.setImageUrl(productRequest.getImageUrl());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        Date now = new Date();
        product.setCreatedDate(now);
        product.setLastModifiedDate(now);
        return productDao.createOrUpdateProduct(product);
    }

    @Override
    public Product updateProduct(Product product, ProductRequest productRequest) {
        product.setProductName(productRequest.getProductName());
        product.setCategory(productRequest.getCategory().name());
        product.setImageUrl(productRequest.getImageUrl());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        Date now = new Date();
        product.setLastModifiedDate(now);
        return productDao.createOrUpdateProduct(product);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
