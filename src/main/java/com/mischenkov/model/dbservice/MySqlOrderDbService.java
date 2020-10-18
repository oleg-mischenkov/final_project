package com.mischenkov.model.dbservice;

import com.mischenkov.entity.*;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.order.ExtendedOrderDao;
import com.mischenkov.model.dao.order.MySqlOrderDao;
import com.mischenkov.model.dao.tariff.ExtendedTariffDao;
import com.mischenkov.model.dao.tariff.MySqlTariffDao;
import com.mischenkov.model.dao.wallet.ExtendedWalletDao;
import com.mischenkov.model.dao.wallet.MySqlWalletDao;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.exception.WalletDoesntHaveEnoughMoneyDBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MySqlOrderDbService extends OrderDbService {

    private static final Logger LOG = Logger.getLogger(MySqlOrderDbService.class);

    @Override
    public void saveOrder(Order order, User user) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedOrderDao orderDao = new MySqlOrderDao();

            // old order check
            Order oldOrder = orderDao.getByUserId(connection, user.getId());

            if ( oldOrder.getId() == 0 ) {
                // save order
                int orderKey = orderDao.saveOrder(connection, order, user);
                order.setId(orderKey);

            } else {
                order.setId( oldOrder.getId() );
                int newTotal = order.getTotal() + oldOrder.getTotal();
                order.setTotal( newTotal );
                orderDao.updateTotal(connection, order);
            }

            // save items
            List<Tariff> itemList = order.getItemList();
             for (Tariff item: itemList) {
                 orderDao.saveItem(connection, item, order);
             }

             // wallet.money to wallet.blockedMoney transaction
            ExtendedWalletDao walletDao = new MySqlWalletDao();
             Wallet wallet = walletDao.getByUserId( connection, user.getId() );

            int orderCost = order.getTotal();

             int walletMoney = wallet.getMoney();
             int walletBlockedMoney = wallet.getBlockedMoney();

             int preOrderCost = orderCost - oldOrder.getTotal();

             wallet.setMoney( walletMoney - preOrderCost );
             wallet.setBlockedMoney( walletBlockedMoney + preOrderCost );

             walletDao.update( connection, wallet, user.getId() );

             connection.commit();

        } catch (SQLException e) {
            LOG.warn("Cant insert Order to the data base", e);
            Dao.quiteRollback(connection);
            throw new DBException("Cant insert Order to the data base", e);

        } finally {
            Dao.quiteClose(connection);
        }

    }

    @Override
    public Order getOrder(int userId, Language language) throws DBException {
        Connection connection = null;

        Order order = null;

        try {
            connection = getConnection();

            ExtendedOrderDao orderDao = new MySqlOrderDao();
            order = orderDao.getByUserId(connection, userId);

            List<Integer> itemKeys = orderDao.getItemsIdByOrder(connection, order);
            List<Tariff> itemList = new ArrayList<>();

            ExtendedTariffDao tariffDao = new MySqlTariffDao();

            for (Integer itemKey: itemKeys) {
                Tariff tariff = tariffDao.getById(connection, language, itemKey);
                itemList.add(tariff);
            }

            order.setItemList( itemList );

        } catch (SQLException e) {
            LOG.warn("Cant obtain an Order for User id[" + userId + "].", e);
            throw new DBException("Cant obtain an Order for User id[" + userId + "].", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return order;
    }

    @Override
    public void deleteItem(int tariffId, Language language, int orderId) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            Order order = null;
            Tariff tariff = null;

            // get current order
            ExtendedOrderDao orderDao = new MySqlOrderDao();
            Order tmpOrder = new Order();
            tmpOrder.setId(orderId);

            order = orderDao.getById(connection, tmpOrder);

            // get tariff
            ExtendedTariffDao tariffDao = new MySqlTariffDao();
            Tariff tmpTariff = new Tariff();
            tmpTariff.setId(tariffId);

            tariff = tariffDao.getById(connection, language, tmpTariff.getId());

            // money transaction
            int tariffCost = tariff.pricePerWeeks(4);
            int orderTotal = order.getTotal();

            order.setTotal( orderTotal - tariffCost );

            orderDao.updateTotal(connection, order);

            // delete item
            orderDao.deleteItem(connection, tariffId, orderId);

            // order check
            List<Integer> itemKeyList = orderDao.getItemsIdByOrder(connection, order);
            if (itemKeyList.size() == 0) {
                orderDao.delete(connection, order);
            }

            connection.commit();

        } catch (SQLException e) {
            LOG.warn("Cant delete the Tariff id[" + tariffId + "].", e);
            Dao.quiteRollback(connection);
            throw new DBException("Cant delete the Tariff id[" + tariffId + "].", e);

        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public void reorder(User user) throws DBException {
        Objects.requireNonNull(user, "reorder(User user), \"user\" is null.");

        Connection connection = null;

        try {
            connection = getConnection();

            Wallet wallet = null;
            Order order = null;

            // obtain user wallet
            ExtendedWalletDao walletDao = new MySqlWalletDao();
            wallet = walletDao.getByUserId(connection, user.getId());
            int walletMoney = wallet.getMoney();

            // obtain user order
            ExtendedOrderDao orderDao = new MySqlOrderDao();
            order = orderDao.getByUserId(connection, user.getId());
            int orderCost = order.getTotal();

            if ( walletMoney < orderCost ) {
                throw new WalletDoesntHaveEnoughMoneyDBException("There is not enough money on the wallet");
            }

            wallet.setMoney( walletMoney - orderCost );
            wallet.setBlockedMoney(orderCost);
            walletDao.update(connection, wallet, user.getId());

            connection.commit();

        } catch (SQLException e) {
            LOG.warn("Invalid reorder by User [" + user.getId() + "].", e);
            Dao.quiteRollback(connection);
            throw new DBException("Invalid reorder by User [" + user.getId() + "].", e);

        } finally {
            Dao.quiteRollback(connection);
        }

    }

    @Override
    public void takeMoney(User user) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            Wallet wallet = null;
            Order order = null;

            ExtendedOrderDao orderDao = new MySqlOrderDao();
            order = orderDao.getByUserId(connection, user.getId());

            ExtendedWalletDao walletDao = new MySqlWalletDao();
            wallet = walletDao.getByUserId(connection, user.getId());

            if ( (order.getId() != 0) && (wallet.getBlockedMoney() > 0) ) {

                int weeksToDays = 4 * 7;

                int orderCost = order.getTotal();
                int walletBlockedMoney = wallet.getBlockedMoney();

                int walletFinalBalance = (walletBlockedMoney - (orderCost / weeksToDays)) >= 0
                        ?  walletBlockedMoney - (orderCost / weeksToDays) : 0;

                wallet.setBlockedMoney( walletFinalBalance );

                walletDao.update(connection, wallet, user.getId());

            }

            connection.commit();

        } catch (SQLException e) {
            LOG.warn("Cant take some money from the User [" + user.getId() + "].", e);
            Dao.quiteRollback(connection);
            throw new DBException("Cant take some money from the User [" + user.getId() + "].", e);

        } finally {
            Dao.quiteClose(connection);
        }

    }
}
