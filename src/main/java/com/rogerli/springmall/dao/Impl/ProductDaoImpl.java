package com.rogerli.springmall.dao.Impl;

import com.rogerli.springmall.dao.ProductDao;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.repository.ProductJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductJpaDao productJpaDao;

    @Override
    public Page<Product> getProducts(ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() == null){
            return productJpaDao.findAllByProductNameContaining(
                    productQueryParams.getSearch(),
                    PageRequest.of((productQueryParams.getPageNumber() - 1),
                            productQueryParams.getLimit(),
                            Sort.by(productQueryParams.getOrderBy()).descending())
            );
        }else {
            return productJpaDao.findAllByCategoryAndProductNameContaining(
                    productQueryParams.getCategory().name(),
                    productQueryParams.getSearch(),
                    PageRequest.of((productQueryParams.getPageNumber()-1),
                            productQueryParams.getLimit(),
                            Sort.by(productQueryParams.getOrderBy()).descending())
            );
        }
    }

    @Override
    public Product getProductById(Integer productId) {
        return productJpaDao.findByProductId(productId);
    }

    @Override
    public Product createOrUpdateProduct(Product product) {
        return productJpaDao.save(product);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        Product product = productJpaDao.findByProductId(productId);
        product.setStock(stock);
        productJpaDao.save(product);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productJpaDao.deleteById(productId);
    }

}
