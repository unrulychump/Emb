package com.example.emb.service.exception;

public class repeatClassException extends ServiceException{
    public repeatClassException() {
        super();
    }

    public repeatClassException(String message) {
        super(message);
    }

    public repeatClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public repeatClassException(Throwable cause) {
        super(cause);
    }

    protected repeatClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
