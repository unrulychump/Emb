package com.example.emb.service.exception;

public class PwdErrorException extends ServiceException{
    public PwdErrorException() {
        super();
    }

    public PwdErrorException(String message) {
        super(message);
    }

    public PwdErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PwdErrorException(Throwable cause) {
        super(cause);
    }

    protected PwdErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
