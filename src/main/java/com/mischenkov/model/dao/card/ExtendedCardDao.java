package com.mischenkov.model.dao.card;

import com.mischenkov.entity.Card;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Extended DAO for the Card entity
 */
public interface ExtendedCardDao extends Dao<Card> {

    Card getByCode(Connection con, int code) throws SQLException;

    void changeMoney(Connection con, int cardId, int money) throws SQLException;

}
