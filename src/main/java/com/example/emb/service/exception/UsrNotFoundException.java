package com.example.emb.service.exception;


public class UsrNotFoundException extends ServiceException{
    public UsrNotFoundException() {
        super();
    }

    public UsrNotFoundException(String message) {
        super(message);
    }

    public UsrNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsrNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UsrNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
