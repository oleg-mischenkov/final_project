package com.mischenkov.model.dao.wallet;

import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseWalletObtainer extends EntityObtainer<Wallet> {
    static Wallet obtainEntity(ResultSet resultSet) throws SQLException {
        Wallet wallet = new Wallet();

        int k = 1;

        int id = resultSet.getInt(k++);
        wallet.setId(id);

        int money = resultSet.getInt(k++);
        wallet.setMoney(money);

        int blockedMoney = resultSet.getInt(k++);
        wallet.setBlockedMoney(blockedMoney);

        int userId = resultSet.getInt(k++);
        wallet.setUserId(userId);

        return wallet;
    }
}
