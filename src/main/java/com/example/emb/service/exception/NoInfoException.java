package com.example.emb.service.exception;

public class NoInfoException extends ServiceException{
    public NoInfoException() {
        super();
    }

    public NoInfoException(String message) {
        super(message);
    }

    public NoInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoInfoException(Throwable cause) {
        super(cause);
    }

    protected NoInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
