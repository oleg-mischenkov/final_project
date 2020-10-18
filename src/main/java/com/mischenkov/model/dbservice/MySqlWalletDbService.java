package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Card;
import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.card.ExtendedCardDao;
import com.mischenkov.model.dao.card.MySqlCardDao;
import com.mischenkov.model.dao.wallet.ExtendedWalletDao;
import com.mischenkov.model.dao.wallet.MySqlWalletDao;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlWalletDbService extends WalletDbService {

    private static final Logger LOG = Logger.getLogger(MySqlWalletDbService.class);

    @Override
    public Wallet getByUserId(int userId) throws DBException {
        Connection connection = null;

        Wallet wallet = null;

        try {
            connection = getConnection();

            ExtendedWalletDao dao = new MySqlWalletDao();
            wallet = dao.getByUserId( connection, userId );

        } catch (SQLException e) {
            LOG.warn("Cant obtain some Wallet from the data base. user id [" + userId + ']', e);
            throw new DBException("Cant obtain some Wallet from the data base. user id [" + userId + ']', e);

        } finally {
            Dao.quiteClose(connection);
        }

        return wallet;
    }

    @Override
    public void cardToWalletTransaction(int cardCode, int userId) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            Card card = null;
            Wallet wallet = null;

            ExtendedCardDao cardDao = new MySqlCardDao();
            card = cardDao.getByCode( connection, cardCode );

            int cardMoney = card.getMoney();
            cardDao.changeMoney( connection, card.getId(), 0 );

            ExtendedWalletDao walletDao = new MySqlWalletDao();
            wallet = walletDao.getByUserId( connection, userId );

            int walletMoney = wallet.getMoney();
            walletMoney += cardMoney;
            wallet.setMoney( walletMoney );

            walletDao.update( connection, wallet, userId);

            connection.commit();

        } catch (SQLException e) {
            LOG.warn("Invalid transaction", e);
            Dao.quiteRollback( connection );
            throw new DBException("Invalid transaction", e);

        } finally {
            Dao.quiteClose(connection);
        }

    }
}
