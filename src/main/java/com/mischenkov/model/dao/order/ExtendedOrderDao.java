package com.mischenkov.model.dao.order;

import com.mischenkov.entity.Order;
import com.mischenkov.entity.Tariff;
import com.mischenkov.entity.User;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 *  Extended DAO for the Order entity
 */
public interface ExtendedOrderDao extends Dao<Order> {

    Order getByUserId(Connection con, int userId) throws SQLException;

    List<Integer> getItemsIdByOrder(Connection con, Order order) throws SQLException;

    int saveOrder(Connection con, Order order, User user) throws SQLException;

    /**
     *  Saves the Order for a specific order.
     *
     * @param con   - connection for the data base
     * @param item  - tariff instance
     * @param order - order instance
     * @throws SQLException
     */
    void saveItem(Connection con, Tariff item, Order order) throws  SQLException;

    void deleteItem(Connection con, int tariffId, int orderId) throws SQLException;

    void updateTotal(Connection con, Order order) throws SQLException;

}
