package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Order;
import com.mischenkov.entity.User;
import com.mischenkov.model.exception.DBException;

public abstract class OrderDbService extends BaseDbService {

    public abstract void saveOrder(Order order, User user) throws DBException;

    public abstract Order getOrder(int userId, Language language) throws DBException;

    public abstract void deleteItem(int tariffId, Language language, int orderId) throws DBException;

    public abstract void reorder(User user) throws DBException;

    public abstract void takeMoney(User user) throws DBException;

}
