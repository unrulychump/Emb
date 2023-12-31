package com.example.emb.service.exception;

public class LeftNotEnoughException extends ServiceException{
    public LeftNotEnoughException() {
        super();
    }

    public LeftNotEnoughException(String message) {
        super(message);
    }

    public LeftNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public LeftNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected LeftNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
