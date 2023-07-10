package com.rogerli.springmall.controller;

import com.rogerli.springmall.constant.ProductCategory;
import com.rogerli.springmall.dto.BuyItem;
import com.rogerli.springmall.dto.ProductQueryParams;
import com.rogerli.springmall.dto.ProductRequest;
import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.service.ProductService;
//import com.rogerli.springmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String getProducts(
            // Filtering
            ProductCategory category,
            @RequestParam(defaultValue = "") String search,
            // Sorting
            @RequestParam(defaultValue = "createdDate") String orderBy,
            // pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "1") @Min(0) Integer pageNumber,
            Model model
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
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/{productId}/{action}")
    public String getProduct(@PathVariable Integer productId,
                             @PathVariable String action,
                             Model model){
        Product product = productService.getProductById(productId);
        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(productId);
        if (product == null){
            model.addAttribute("error", "此商品已完售");
        }else {
            model.addAttribute("product", product);
            model.addAttribute("buyItem", buyItem);
        }
        switch (action) {
            case "update":
                ProductRequest productRequest = new ProductRequest(product);
                model.addAttribute("productRequest", productRequest);
                return "updateproduct";
            case "delete":
                return deleteProduct(productId, model);
            case "add":
                return "checkorder";
            default:
                return null;
        }
    }

    @PostMapping("/products")
    public String createProduct(@Valid ProductRequest productRequest, Model model){
        productService.createProduct(productRequest);
        return getProducts(null, "", "createdDate", 5, 1, model);
    }

    @PutMapping("/products/{productId}")
    public String updateProduct(@PathVariable Integer productId,
                                @Valid ProductRequest productRequest,
                                Model model){
        // 檢查product是否存在
        Product product = productService.getProductById(productId);
        if (product == null){
            model.addAttribute("error","查無此商品");
            return "products";

        }
        // 修改商品的數據
        productService.updateProduct(product, productRequest);
        return getProducts(null, "", "createdDate", 5, 1, model);
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Integer productId, Model model){
        Product product = productService.getProductById(productId);
        if (product != null){
            product.setStock(0);
            productService.deleteProductById(product);
        }
        return getProducts(null, "", "createdDate", 5, 1, model);
    }
}
