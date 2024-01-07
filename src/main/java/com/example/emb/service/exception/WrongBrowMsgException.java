package com.example.emb.service.exception;

public class WrongBrowMsgException extends ServiceException{
    public WrongBrowMsgException() {
        super();
    }

    public WrongBrowMsgException(String message) {
        super(message);
    }

    public WrongBrowMsgException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongBrowMsgException(Throwable cause) {
        super(cause);
    }

    protected WrongBrowMsgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
