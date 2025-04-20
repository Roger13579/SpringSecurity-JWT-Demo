package com.rogerli.springmall.exception;

/**
 * Exception thrown when there are authentication issues (JWT token, credentials, etc.)
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}