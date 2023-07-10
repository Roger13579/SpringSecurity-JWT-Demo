package com.rogerli.springmall.dto;

import com.rogerli.springmall.constant.ProductCategory;
import lombok.Data;

@Data
public class OrderQueryParams {
    Integer userId;
    boolean iscart;
    Integer limit;
    Integer pageNumber;
}
