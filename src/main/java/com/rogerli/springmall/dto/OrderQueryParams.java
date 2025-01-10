package com.rogerli.springmall.dto;

import lombok.Data;

@Data
public class OrderQueryParams {
    Integer userId;
    boolean iscart;
    Integer limit;
    Integer pageNumber;
}
