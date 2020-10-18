package com.mischenkov.model.dao.wallet;

import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ExtendedWalletDao extends Dao<Wallet> {

    void save(Connection con, Wallet wallet, int userId) throws SQLException;

    void update(Connection con, Wallet wallet, int userId) throws SQLException;

    Wallet getByUserId(Connection con, int userId) throws SQLException;

}
