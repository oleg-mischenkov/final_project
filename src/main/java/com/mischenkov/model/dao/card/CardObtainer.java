package com.mischenkov.model.dao.card;

import com.mischenkov.entity.Card;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardObtainer implements EntityObtainer<Card> {
    @Override
    public Card obtain(ResultSet rs) throws SQLException {
        Card card = new Card();

        if ( rs.next() ) {
            card = BaseCardObtainer.obtainEntity(rs);
        }

        return card;
    }
}
