package com.qlish.qlish_api.exception;

public class CustomQlishException extends RuntimeException {

    public CustomQlishException(String message) {
        super(message);
    }
    public CustomQlishException(String message, Throwable cause) {
        super(message, cause);
    }
}
