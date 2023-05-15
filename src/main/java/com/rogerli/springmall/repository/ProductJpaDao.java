package com.rogerli.springmall.repository;

import com.rogerli.springmall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaDao extends JpaRepository<Product,Long> {

    Product findByProductId(Integer prodyctId);
    Page<Product> findAllByProductNameContaining(String productName, Pageable pageable);
    Page<Product> findAllByCategoryAndProductNameContaining(String category, String productName, Pageable pageable);

}
