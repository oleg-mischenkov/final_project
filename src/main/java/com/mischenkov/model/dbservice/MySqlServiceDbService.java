package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.service.ExtendedServiceDao;
import com.mischenkov.model.dao.service.MySqlServiceDao;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySqlServiceDbService extends ServiceDbService {

    private static final Logger LOG = Logger.getLogger(MySqlServiceDbService.class);

    @Override
    public List<Service> getAll(Language language) throws DBException {
        List<Service> serviceList = new ArrayList<>();

        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedServiceDao dao = new MySqlServiceDao();
            serviceList.addAll(
                    dao.getAll(connection, language)
            );

        } catch (SQLException e) {
            LOG.warn("List<Service> getAll(Language language) caused an exception.", e);
            throw new DBException("getAll(Language language) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return serviceList;
    }

    @Override
    public void updateServiceActive(int serviceId, int active) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedServiceDao dao = new MySqlServiceDao();
            dao.updateServiceActive(connection, serviceId, active);

            connection.commit();
        } catch (SQLException e) {
            LOG.warn("void updateServiceActive(int serviceId, int active) caused an exception.", e);
            Dao.quiteRollback(connection);
            throw new DBException("updateServiceActive(int serviceId, int active) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public void saveService(Map<Integer, Service> langIdOnService) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedServiceDao dao = new MySqlServiceDao();
            dao.saveService(connection, langIdOnService);

            connection.commit();

        } catch (SQLException e) {
            LOG.warn("saveService(Map<Integer, Service> langIdOnService) caused an exception.", e);
            Dao.quiteRollback(connection);
            throw new DBException("void saveService(Map<Integer, Service> langIdOnService) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public void delete(Service service) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();

            Dao<Service> dao = new MySqlServiceDao();
            dao.delete(connection, service);

            connection.commit();
        } catch (SQLException e) {
            LOG.warn("delete(Service service) caused an exception.", e);
            Dao.quiteRollback(connection);
            throw new DBException("void delete(Service service) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public Service getById(Service entity, Language language) throws DBException {
        Connection connection = null;

        Service service = null;

        try {
            connection = getConnection();

            ExtendedServiceDao dao = new MySqlServiceDao();
            service = dao.getById(connection, entity, language);

        } catch (SQLException e) {
            LOG.warn("getById(Service entity, Language language) caused an exception", e);
            throw new DBException("Cant obtain some Service with serviceId [" + entity.getId() + "] and languageId [" + language.getId() + "].");

        } finally {
            Dao.quiteClose(connection);
        }

        return service;
    }
}
