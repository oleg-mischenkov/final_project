package com.mischenkov.model.exception;

public class NotThatIdDBException extends DBException {
    public NotThatIdDBException(String msg) {
        super(msg);
    }

    public NotThatIdDBException(String msg, Throwable e) {
        super(msg, e);
    }
}
