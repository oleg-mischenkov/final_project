package com.mischenkov.model.exception;

/**
 *  Exceptional situation if there is no entity with this ID in the database.
 */
public class NotThatIdDBException extends DBException {
    public NotThatIdDBException(String msg) {
        super(msg);
    }

    public NotThatIdDBException(String msg, Throwable e) {
        super(msg, e);
    }
}
