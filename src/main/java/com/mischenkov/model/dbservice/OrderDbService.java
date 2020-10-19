package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Order;
import com.mischenkov.entity.User;
import com.mischenkov.model.exception.DBException;

/**
 * Interface for working with an order
 */
public abstract class OrderDbService extends BaseDbService {

    /**
     *  The method saves the order to the database.
     *
     * @param order - order to be saved
     * @param user  - user for which you want to save the order
     * @throws DBException
     */
    public abstract void saveOrder(Order order, User user) throws DBException;

    /**
     *  The method receives the order of a specific user.
     *
     * @param userId - current User id
     * @param language - current interface language
     * @return - receives the order of a specific user
     * @throws DBException
     */
    public abstract Order getOrder(int userId, Language language) throws DBException;

    public abstract void deleteItem(int tariffId, Language language, int orderId) throws DBException;

    /**
     *  The method initiates the reorder of the old order.
     *
     * @param user - user for which you want to reorder
     * @throws DBException
     */
    public abstract void reorder(User user) throws DBException;

    /**
     *  The method withdraws money from a specific user.
     *
     * @param user
     * @throws DBException
     */
    public abstract void takeMoney(User user) throws DBException;

}
