package com.thangavel.authentication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid or expired token"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}

