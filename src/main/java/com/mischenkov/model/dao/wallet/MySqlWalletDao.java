package com.mischenkov.model.dao.wallet;

import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MySqlWalletDao implements ExtendedWalletDao {

    private static final Logger LOG = Logger.getLogger(MySqlWalletDao.class);

    private static final String SQL_UPDATE_WALLET = "UPDATE `wallets` SET `money` = ?, `blocked_money` = ? WHERE `users_user_id` = ?";
    private static final String SQL_INSERT_WALLET = "INSERT INTO `wallets` (`money`, `blocked_money`, `users_user_id`) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_WALLET_BY_USER_ID = "SELECT `wallet_id`, `money`, `blocked_money`, `users_user_id` FROM `wallets` WHERE `users_user_id` = ?";

    @Override
    public void save(Connection con, Wallet wallet, int userId) throws SQLException {
        Objects.requireNonNull(con, "save(Connection con, Wallet wallet, int userId), \"con\" is null");
        Objects.requireNonNull(wallet, "save(Connection con, Wallet wallet, int userId), \"wallet\" is null");

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(SQL_INSERT_WALLET);

            int money = wallet.getMoney();
            int blockedMoney = wallet.getBlockedMoney();

            int k = 1;
            preparedStatement.setInt(k++, money);
            preparedStatement.setInt(k++, blockedMoney);
            preparedStatement.setInt(k++, userId);

            preparedStatement.executeUpdate();

        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public void update(Connection con, Wallet wallet, int userId) throws SQLException {
        Objects.requireNonNull(con, " update(Connection con, Wallet wallet, int userId), \"con\" is null");
        Objects.requireNonNull(wallet, " update(Connection con, Wallet wallet, int userId), \"wallet\" is null");

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(SQL_UPDATE_WALLET);

            int money = wallet.getMoney();
            int blockedMoney = wallet.getBlockedMoney();

            preparedStatement.setInt(1, money);
            preparedStatement.setInt(2, blockedMoney);
            preparedStatement.setInt(3, userId);

            preparedStatement.executeUpdate();

        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public Wallet getByUserId(Connection con, int userId) throws SQLException {
        Objects.requireNonNull(con, "getByUserId(Connection con, int userId), \"con\" is null.");

        Wallet wallet = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_WALLET_BY_USER_ID);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<Wallet> obtainer = new WalletObtainer();
            wallet = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return wallet;
    }

    @Override
    public List<Wallet> getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Wallet getById(Connection con, Wallet entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, Wallet entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Wallet entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
