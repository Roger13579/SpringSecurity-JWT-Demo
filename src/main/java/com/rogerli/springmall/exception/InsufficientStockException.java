package com.rogerli.springmall.exception;


import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {

    private Integer productId;
    private Integer requestedQuantity;
    private Integer availableStock;

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(Integer productId, Integer requestedQuantity, Integer availableStock) {
        super(String.format("Insufficient stock for product ID %d: requested %d, available %d", 
                productId, requestedQuantity, availableStock));
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableStock = availableStock;
    }

}