package com.rogerli.springmall.controller;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.model.ProductView;
import com.rogerli.springmall.service.ProductService;
//import com.rogerli.springmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // Filtering
            @RequestParam(defaultValue = "1=1") ProductCategory category,
            @RequestParam(defaultValue = "") String search,
            // Sorting
            @RequestParam(defaultValue = "createdDate") String orderBy,
            // pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "1") @Min(0) Integer pageNumber
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setLimit(limit);
        productQueryParams.setPageNumber(pageNumber);
        // get productlist
        Page<Product> products = productService.getProducts(productQueryParams);
        products.getPageable();
        // get product total count
        Integer total = productService.countProduct(productQueryParams);

        // paging
//        Page<ProductView> page = new Page<>();
//        page.setLimit(limit);
//        page.setOffset(offset);
//        page.setTotal(total);
//        page.setResults(productList);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductView> getProduct(@PathVariable Integer productId){
        ProductView product = productService.getProductById(productId);

        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductView> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        ProductView product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductView> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        // 檢查product是否存在
        ProductView product = productService.getProductById(productId);
        if (product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 修改商品的數據
        productService.updateProduct(productId,productRequest);
        ProductView updatedProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
