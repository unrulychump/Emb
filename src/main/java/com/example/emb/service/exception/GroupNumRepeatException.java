package com.example.emb.service.exception;

public class GroupNumRepeatException extends ServiceException{
    public GroupNumRepeatException() {
        super();
    }

    public GroupNumRepeatException(String message) {
        super(message);
    }

    public GroupNumRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupNumRepeatException(Throwable cause) {
        super(cause);
    }

    protected GroupNumRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
