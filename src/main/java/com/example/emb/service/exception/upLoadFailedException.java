package com.example.emb.service.exception;

public class upLoadFailedException extends RuntimeException{
    public upLoadFailedException() {
        super();
    }

    public upLoadFailedException(String message) {
        super(message);
    }

    public upLoadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public upLoadFailedException(Throwable cause) {
        super(cause);
    }

    protected upLoadFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
