package com.example.emb.service.exception;

public class ErrorGroupNameException  extends ServiceException{
    public ErrorGroupNameException() {
        super();
    }

    public ErrorGroupNameException(String message) {
        super(message);
    }

    public ErrorGroupNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorGroupNameException(Throwable cause) {
        super(cause);
    }

    protected ErrorGroupNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
