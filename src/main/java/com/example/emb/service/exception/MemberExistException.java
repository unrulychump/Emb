package com.example.emb.service.exception;

public class MemberExistException extends ServiceException{
    public MemberExistException() {
        super();
    }

    public MemberExistException(String message) {
        super(message);
    }

    public MemberExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberExistException(Throwable cause) {
        super(cause);
    }

    protected MemberExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
