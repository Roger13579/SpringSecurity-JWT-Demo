package com.rogerli.springmall.rowMapper;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.model.ProductView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductView> {


    @Override
    public ProductView mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductView product = new ProductView();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setCategory(ProductCategory.valueOf(rs.getString("category")));
        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
        return product;
    }

    public ProductView mapRow(Product product){
        ProductView productView = new ProductView();
        productView.setProductId(product.getProductId());
        productView.setProductName(product.getProductName());
        productView.setCategory(ProductCategory.valueOf(product.getCategory()));
        productView.setImageUrl(product.getImageUrl());
        productView.setPrice(product.getPrice());
        productView.setStock(product.getStock());
        productView.setDescription(product.getDescription());
        productView.setCreatedDate(product.getCreatedDate());
        productView.setLastModifiedDate(product.getLastModifiedDate());
        return productView;
    }
}
