package com.ssafyebs.customerback.global.exception;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException(String message){
        super(message);
    }
}
