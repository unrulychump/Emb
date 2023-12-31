package com.example.emb.service.exception;

public class GroupExistException extends ServiceException{
    public GroupExistException() {
        super();
    }

    public GroupExistException(String message) {
        super(message);
    }

    public GroupExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupExistException(Throwable cause) {
        super(cause);
    }

    protected GroupExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
