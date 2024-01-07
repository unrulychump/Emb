package com.example.emb.service.exception;

public class FileTypeErrorException  extends ServiceException{
    public FileTypeErrorException() {
        super();
    }

    public FileTypeErrorException(String message) {
        super(message);
    }

    public FileTypeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeErrorException(Throwable cause) {
        super(cause);
    }

    protected FileTypeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
