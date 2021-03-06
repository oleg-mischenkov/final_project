package com.mischenkov.model.exception;

/**
 *  An exceptional situation if the user's wallet does not have enough money.
 */
public class WalletDoesntHaveEnoughMoneyDBException extends DBException {
    public WalletDoesntHaveEnoughMoneyDBException(Exception e) {
        super(e);
    }

    public WalletDoesntHaveEnoughMoneyDBException(String msg) {
        super(msg);
    }

    public WalletDoesntHaveEnoughMoneyDBException(String msg, Throwable e) {
        super(msg, e);
    }
}
