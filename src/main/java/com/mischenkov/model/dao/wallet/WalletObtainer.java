package com.mischenkov.model.dao.wallet;

import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletObtainer implements EntityObtainer<Wallet> {
    @Override
    public Wallet obtain(ResultSet rs) throws SQLException {
        rs.next();
        return BaseWalletObtainer.obtainEntity(rs);
    }
}
