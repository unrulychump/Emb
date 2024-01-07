package com.example.emb.service.exception;

public class FileTooBigException extends ServiceException{
    public FileTooBigException() {
        super();
    }

    public FileTooBigException(String message) {
        super(message);
    }

    public FileTooBigException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTooBigException(Throwable cause) {
        super(cause);
    }

    protected FileTooBigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
