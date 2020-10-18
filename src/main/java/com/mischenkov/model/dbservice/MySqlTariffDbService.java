package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.SqlOrderBy;
import com.mischenkov.model.dao.tariff.ExtendedTariffDao;
import com.mischenkov.model.dao.tariff.MySqlTariffDao;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySqlTariffDbService extends TariffDbService {

    private static final Logger LOG = Logger.getLogger(MySqlTariffDbService.class);

    @Override
    public Tariff getById(Language language, int tariffId) throws DBException {
        Connection connection = null;

        Tariff tariff = null;

        try {
            connection = getConnection();

            ExtendedTariffDao dao = new MySqlTariffDao();
            tariff = dao.getById(connection, language, tariffId);

        } catch (SQLException e) {
            LOG.warn("Cant obtain some Tariff [" + tariffId + "] from data base", e);
            throw new DBException("Cant obtain some Tariff [" + tariffId + "] from data base", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return tariff;
    }

    @Override
    public List<Tariff> getAll(Language language, int serviceId) throws DBException {
        List<Tariff> tariffList = new ArrayList<>();

        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedTariffDao dao = new MySqlTariffDao();
            tariffList.addAll(
                    dao.getAll(connection, language, serviceId)
            );

        } catch (SQLException e) {
            LOG.warn("List<Service> getAll(Language language, int serviceId) caused an exception.", e);
            throw new DBException("getAll(Language language, int serviceId) caused an exception.", e);
        } finally {
            Dao.quiteClose(connection);
        }

        return tariffList;
    }

    @Override
    public List<Tariff> getAll(Language language, int serviceId, String sortField, SqlOrderBy orderBy, int position, int count) throws DBException {
        List<Tariff> tariffList = new ArrayList<>();

        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedTariffDao dao = new MySqlTariffDao();
            tariffList.addAll(
                    dao.getAll(connection, language, serviceId, sortField, orderBy, position, count)
            );

        } catch (SQLException e) {
            LOG.warn("List<Service> getAll(Language language, int serviceId, String sortField, SqlOrderBy " +
                    "orderBy, int position, int count) caused an exception. " +
                    "language = [" + language + "]; serviceId = [" + serviceId + "]; sortField = [" + sortField + "]; orderBy =[" +
                    orderBy + "]; position = [" + position + "]; count = [" + count + ']', e);
            throw new DBException("getAll(Language language, int serviceId, String sortField, SqlOrderBy orderBy, " +
                    "int position, int count) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return tariffList;
    }

    @Override
    public void updateTariffActive(int tariffId, int active) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedTariffDao dao = new MySqlTariffDao();
            dao.updateTariffActive(connection, tariffId, active);

            connection.commit();
        } catch (SQLException e) {
            LOG.warn("void updateTariffActive(int tariffId, int active) caused an exception.", e);
            Dao.quiteRollback(connection);
            throw new DBException("updateTariffActive(int tariffId, int active) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public void saveTariff(Map<Integer, Tariff> langIdOnTariff, int serviceId) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedTariffDao dao = new MySqlTariffDao();
            dao.saveTariff(connection, langIdOnTariff, serviceId);

            connection.commit();

        } catch (SQLException e) {
            LOG.warn("saveTariff(Map<Integer, Tariff> langIdOnTariff, int serviceId) caused an exception.", e);
            Dao.quiteRollback(connection);
            throw new DBException("void saveTariff(Map<Integer, Tariff> langIdOnTariff, int serviceId) caused an exception.", e);
        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public void delete(Tariff tariff) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            Dao<Tariff> dao = new MySqlTariffDao();
            dao.delete(connection, tariff);

            connection.commit();
        } catch (SQLException e) {
            LOG.warn("delete(Tariff tariff) caused an exception.", e);
            Dao.quiteRollback(connection);
            throw new DBException("void delete(Tariff tariff) caused an exception.", e);
        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public int count(Service service) throws DBException {
        Connection connection = null;

        int count = 0;

        try {
            connection = getConnection();

            ExtendedTariffDao dao = new MySqlTariffDao();
            count = dao.count(connection, service);

        } catch (SQLException e) {
            LOG.warn("count(Tariff tariff) caused an exception.", e);
            throw new DBException("int count(Tariff tariff) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return count;
    }
}
