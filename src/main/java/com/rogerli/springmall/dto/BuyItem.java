package com.rogerli.springmall.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class BuyItem {
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;
}
