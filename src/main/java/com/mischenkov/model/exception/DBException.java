package com.mischenkov.model.exception;

/**
 *  The general class of exceptions for high-level business logic.
 */
public class DBException extends Exception{

    public DBException(Exception e) {
        super(e);
    }

    public DBException(String msg) {
        super(msg);
    }

    public DBException(String msg, Throwable e) {
        super(msg, e);
    }
}
