package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Card;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.card.ExtendedCardDao;
import com.mischenkov.model.dao.card.MySqlCardDao;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlCardDbService extends CardDbService {

    private static final Logger LOG = Logger.getLogger(MySqlCardDbService.class);

    @Override
    public Card getByCode(int cardCode) throws DBException {
        Connection connection = null;

        Card card = null;

        try {
            connection = getConnection();

            ExtendedCardDao dao = new MySqlCardDao();
            card = dao.getByCode(connection, cardCode);

        } catch (SQLException e) {
            LOG.warn("Cant obtain some Card witch has code [" + cardCode + ']', e);
            throw new DBException("Cant obtain some Card witch has code [" + cardCode + ']', e);

        } finally {
            Dao.quiteClose(connection);
        }

        return card;
    }
}
