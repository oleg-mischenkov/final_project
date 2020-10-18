package com.mischenkov.model.dao.card;

import com.mischenkov.entity.Card;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MySqlCardDao implements ExtendedCardDao {

    private static final Logger LOG = Logger.getLogger(MySqlCardDao.class);

    private static final String SQL_SELECT_CARD_BY_CODE = "SELECT `card_id`, `money`, `card_code` FROM `money_cards` WHERE `card_code` = ?";
    private static final String SQL_UPDATE_MONEY_BY_CARD_ID = "UPDATE `money_cards` SET `money` = ? WHERE `card_id` = ?";

    @Override
    public Card getByCode(Connection con, int code) throws SQLException {
        Objects.requireNonNull(con, "getByCode(Connection con, int code), \"con\" is null");

        Card card = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_CARD_BY_CODE);
            preparedStatement.setInt(1, code);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<Card> obtainer = new CardObtainer();
            card = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return card;
    }

    @Override
    public void changeMoney(Connection con, int cardId, int money) throws SQLException {
        Objects.requireNonNull(con, "changeMoney(Connection con, int cardId, int money), \"con\" is null");

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(SQL_UPDATE_MONEY_BY_CARD_ID);
            preparedStatement.setInt(1, money);
            preparedStatement.setInt(2, cardId);

            preparedStatement.executeUpdate();

        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public List<Card> getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Card getById(Connection con, Card entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, Card entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Card entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
