package com.qlish.qlish_api.exception;

public class CustomDatabaseException extends RuntimeException {
    public CustomDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
