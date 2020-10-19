package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Card;
import com.mischenkov.model.exception.DBException;

public abstract class CardDbService extends BaseDbService {

    /**
     *  The method returns an instance of a recharge card by entity id.
     *
     * @param cardCode  - card id
     * @return  - specific card
     * @throws DBException
     */
    public abstract Card getByCode(int cardCode) throws DBException;

}
