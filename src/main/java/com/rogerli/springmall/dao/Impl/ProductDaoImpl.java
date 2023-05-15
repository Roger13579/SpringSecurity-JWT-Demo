package com.rogerli.springmall.dao.Impl;

import com.rogerli.springmall.dao.ProductDao;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.model.ProductView;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.repository.ProductJpaDao;
import com.rogerli.springmall.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ProductJpaDao productJpaDao;

    @Override
    public Page<Product> getProducts(ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() == null){
            return productJpaDao.findAllByProductNameContaining(
                    productQueryParams.getSearch(),
                    PageRequest.of(productQueryParams.getPageNumber()-1
                            , productQueryParams.getLimit()
                            , Sort.by(productQueryParams.getOrderBy()).descending())
            );
        }else {
            return productJpaDao.findAllByCategoryAndProductNameContaining(
                    productQueryParams.getCategory().name(),
                    productQueryParams.getSearch(),
                    PageRequest.of(productQueryParams.getPageNumber()-1
                            , productQueryParams.getLimit()
                            , Sort.by(productQueryParams.getOrderBy()).descending())
            );
        }
    }

    @Override
    public ProductView getProductById(Integer productId) {
        Product product = productJpaDao.findByProductId(productId);
        if (product == null) {
            return null;
        } else {
            return new ProductRowMapper().mapRow(product);
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                " WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate " +
                " WHERE product_id = :productId ";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("stock", stock);
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();
        addFilteringSql(sql,map,productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    private String addFilteringSql(String sql, Map<String,Object> map, ProductQueryParams productQueryParams){
        if (productQueryParams.getCategory() != null){
            sql = sql + " AND category = :category ";
            map.put("category",productQueryParams.getCategory().name());
        }
        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search ";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }
        return sql;
    }

}
