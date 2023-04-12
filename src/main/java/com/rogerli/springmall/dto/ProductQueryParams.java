package com.rogerli.springmall.dto;

import com.rogerli.springmall.constant.ProductCategory;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ProductQueryParams {
    ProductCategory category;
    String search;
    String orderBy;
    String sort;
    Integer limit;
    Integer offset;
}
