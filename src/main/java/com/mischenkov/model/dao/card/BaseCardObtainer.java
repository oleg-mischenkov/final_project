package com.mischenkov.model.dao.card;

import com.mischenkov.entity.Card;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseCardObtainer extends EntityObtainer<Card> {

    static Card obtainEntity(ResultSet resultSet) throws SQLException {
        Card card = new Card();

        int id = resultSet.getInt(1);
        card.setId(id);

        int money = resultSet.getInt(2);
        card.setMoney(money);

        int code = resultSet.getInt(3);
        card.setCode(code);

        return card;
    }
}
