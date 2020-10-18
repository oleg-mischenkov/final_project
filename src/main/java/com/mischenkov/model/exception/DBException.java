package com.mischenkov.model.exception;

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
