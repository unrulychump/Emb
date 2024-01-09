package com.example.emb.service.exception;

public class SmsErrorException extends RuntimeException{
    public SmsErrorException() {
        super();
    }

    public SmsErrorException(String message) {
        super(message);
    }

    public SmsErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsErrorException(Throwable cause) {
        super(cause);
    }

    protected SmsErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
