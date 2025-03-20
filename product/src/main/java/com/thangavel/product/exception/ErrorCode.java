package com.thangavel.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ProductNotFoundException(HttpStatus.NOT_FOUND, "Product with ID not found"),
    InvalidUserException(HttpStatus.FORBIDDEN, "Invalid User");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
