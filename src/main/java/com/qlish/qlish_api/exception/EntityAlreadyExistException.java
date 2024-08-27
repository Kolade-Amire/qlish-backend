package com.qlish.qlish_api.exception;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message){
        super(message);
    }
}
