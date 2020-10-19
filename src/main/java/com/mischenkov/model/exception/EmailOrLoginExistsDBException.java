package com.mischenkov.model.exception;

/**
 *  Exceptional situation if there is no such user or mail in the database.
 */
public class EmailOrLoginExistsDBException extends DBException {
    public EmailOrLoginExistsDBException(String msg) {
        super(msg);
    }

    public EmailOrLoginExistsDBException(String msg, Throwable e) {
        super(msg, e);
    }
}
