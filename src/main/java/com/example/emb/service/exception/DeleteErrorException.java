package com.example.emb.service.exception;

public class DeleteErrorException extends ServiceException{
    public DeleteErrorException() {
        super();
    }

    public DeleteErrorException(String message) {
        super(message);
    }

    public DeleteErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteErrorException(Throwable cause) {
        super(cause);
    }

    protected DeleteErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
