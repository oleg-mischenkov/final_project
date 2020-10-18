package com.mischenkov.model.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    Logger LOG = Logger.getLogger(Dao.class);

    List<T> getAll(Connection con) throws SQLException;

    T getById(Connection con, T entity) throws SQLException;

    int save(Connection con, T entity) throws SQLException;

    void delete(Connection con, T entity) throws SQLException;

    int count(Connection con) throws SQLException;

    static void quiteClose(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                LOG.warn("Some problem with Entity closing");
            }
        }
    }

    static void quiteRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOG.warn("Some problem with closing of Connection");
            }
        }
    }
}
