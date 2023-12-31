package com.example.emb.service.exception;

public class NoNameReceivedException extends ServiceException{
    public NoNameReceivedException() {
        super();
    }

    public NoNameReceivedException(String message) {
        super(message);
    }

    public NoNameReceivedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNameReceivedException(Throwable cause) {
        super(cause);
    }

    protected NoNameReceivedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

