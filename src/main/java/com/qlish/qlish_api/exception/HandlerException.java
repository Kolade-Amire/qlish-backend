package com.qlish.qlish_api.exception;

public class HandlerException extends RuntimeException{
    public HandlerException(String message, Throwable cause){
        super(message, cause);
    }
}
