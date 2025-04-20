package com.rogerli.springmall.exception;

/**
 * Exception thrown when attempting to order more items than available in stock
 */
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

    public Integer getProductId() {
        return productId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }
}