package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Wallet;
import com.mischenkov.model.exception.DBException;

public abstract class WalletDbService extends BaseDbService {

    public abstract Wallet getByUserId(int userId) throws DBException;

    public abstract void cardToWalletTransaction(int cardCode, int userId) throws DBException;

}
