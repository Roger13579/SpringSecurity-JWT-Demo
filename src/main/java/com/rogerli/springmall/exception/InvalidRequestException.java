package com.rogerli.springmall.exception;

/**
 * Exception thrown when a request is invalid but not caught by @Valid annotations
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String field, String message) {
        super(String.format("Invalid value for field '%s': %s", field, message));
    }
}