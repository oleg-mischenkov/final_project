package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Card;
import com.mischenkov.model.exception.DBException;

public abstract class CardDbService extends BaseDbService {

    public abstract Card getByCode(int cardCode) throws DBException;

}
