package com.mischenkov.model.dao.order;

import com.mischenkov.entity.Order;
import com.mischenkov.entity.Tariff;
import com.mischenkov.entity.User;
import com.mischenkov.model.dao.CommonFunctionalityDao;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MySqlOrderDao implements ExtendedOrderDao {

    private static final Logger LOG = Logger.getLogger(MySqlOrderDao.class);

    private static final String SQL_SELECT_ORDER_BY_ORDER_ID = "SELECT `order_id`, `total`, `user_id` FROM `orders` WHERE `order_id` = ?";
    private static final String SQL_SELECT_ORDER_BY_USER_ID = "SELECT `order_id`, `total`, `user_id` FROM `orders` WHERE `user_id` = ?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO `orders` (`total`, `user_id`) VALUES (?, ?)";
    private static final String SQL_INSERT_ITEM = "INSERT INTO `items` (`tariff_id`, `order_id`) VALUES (?, ?)";
    private static final String SQL_SELECT_ITEM_IDS_BY_ORDER = "SELECT `tariff_id` FROM `items` WHERE `order_id` = ?";
    private static final String SQL_DELETE_ITEM = "DELETE FROM `items` WHERE `tariff_id`=? AND `order_id`=?";
    private static final String SQL_UPDATE_ORDER_TOTAL_VALUE = "UPDATE `orders` SET `total` = ? WHERE `order_id` = ?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM `orders` WHERE `order_id` = ?";

    @Override
    public Order getByUserId(Connection con, int userId) throws SQLException {
        Objects.requireNonNull(con, "getByUserId(Connection con, int userId), \"con\" is null.");

        Order order = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_ORDER_BY_USER_ID);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<Order> obtainer = new OrderObtainer();
            order = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return order;
    }

    @Override
    public List<Integer> getItemsIdByOrder(Connection con, Order order) throws SQLException {
        Objects.requireNonNull(con, "getItemsIdByOrder(Connection con, Order order), \"con\" is null.");
        Objects.requireNonNull(order, "getItemsIdByOrder(Connection con, Order order), \"order\" is null.");

        List<Integer> ids = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_ITEM_IDS_BY_ORDER);
            preparedStatement.setInt(1, order.getId());

            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                int tariffId = resultSet.getInt(1);
                ids.add( tariffId );
            }

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return ids;
    }

    @Override
    public int saveOrder(Connection con, Order order, User user) throws SQLException {
        Objects.requireNonNull(con, "saveOrder(Connection con, Order order), \"con\" is null.");
        Objects.requireNonNull(order, "saveOrder(Connection con, Order order), \"order\" is null.");
        Objects.requireNonNull(user, "saveOrder(Connection con, Order order), \"user\" is null.");

        int key = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            LOG.debug("order.getTotal() == " + order.getTotal());
            preparedStatement.setInt(1, order.getTotal());
            LOG.debug("user.getId() == " + user.getId());
            preparedStatement.setInt(2, user.getId());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            key = EntityObtainer.obtainKey(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return key;
    }

    @Override
    public void saveItem(Connection con, Tariff item, Order order) throws SQLException {
        Objects.requireNonNull(con, "saveItems(Connection con, Tariff item, Order order), \"con\" is null.");
        Objects.requireNonNull(item, "saveItems(Connection con, Tariff item, Order order), \"item\" is null.");
        Objects.requireNonNull(order, "saveItems(Connection con, Tariff item, Order order), \"order\" is null.");

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = con.prepareStatement(SQL_INSERT_ITEM);
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setInt(2, order.getId());

            preparedStatement.executeUpdate();

        } finally {
            Dao.quiteClose(preparedStatement);
        }

    }

    @Override
    public void deleteItem(Connection con, int tariffId, int orderId) throws SQLException {
        Objects.requireNonNull(con, "deleteItem(Connection con, int tariffId, int orderId)");

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(SQL_DELETE_ITEM);
            int k = 1;
            preparedStatement.setInt(k++, tariffId);
            preparedStatement.setInt(k++, orderId);

            preparedStatement.executeUpdate();

        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public void updateTotal(Connection con, Order order) throws SQLException {
        Objects.requireNonNull(con, "updateTotal(Connection con, Order order), \"con\" is null.");
        Objects.requireNonNull(order, "updateTotal(Connection con, Order order), \"order\" is null.");

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(SQL_UPDATE_ORDER_TOTAL_VALUE);
            preparedStatement.setInt(1, order.getTotal());
            preparedStatement.setInt(2, order.getId());

            preparedStatement.executeUpdate();

        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public List getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order getById(Connection con, Order entity) throws SQLException {
        Objects.requireNonNull(con, "getById(Connection con, Order entity), \"con\" is null.");
        Objects.requireNonNull(entity, "getById(Connection con, Order entity), \"entity\" is null.");

        Order order = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_ORDER_BY_ORDER_ID);
            preparedStatement.setInt(1, entity.getId());

            resultSet = preparedStatement.executeQuery();
            EntityObtainer<Order> obtainer = new OrderObtainer();

            order = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return order;
    }

    @Override
    public int save(Connection con, Order entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Order entity) throws SQLException {
        Objects.requireNonNull(con, "delete(Connection con, Order entity), \"con\" is null.");
        Objects.requireNonNull(entity, "delete(Connection con, Order entity), \"entity\" is null.");

        CommonFunctionalityDao.deleteEntity(con, entity.getId(), SQL_DELETE_ORDER);
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
