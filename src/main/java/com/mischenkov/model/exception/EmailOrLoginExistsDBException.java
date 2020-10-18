package com.mischenkov.model.exception;

public class EmailOrLoginExistsDBException extends DBException {
    public EmailOrLoginExistsDBException(String msg) {
        super(msg);
    }

    public EmailOrLoginExistsDBException(String msg, Throwable e) {
        super(msg, e);
    }
}
