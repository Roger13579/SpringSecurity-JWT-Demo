package com.rogerli.springmall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UpdateOrderRequest {
    @NotEmpty
    private Integer orderId;
    @NotEmpty
    private Integer quantity;
    @NotEmpty
    private Integer productId;

}
