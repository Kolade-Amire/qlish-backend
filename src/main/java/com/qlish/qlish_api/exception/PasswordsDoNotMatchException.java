package com.qlish.qlish_api.exception;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException(String message){
        super(message);
    }
}
