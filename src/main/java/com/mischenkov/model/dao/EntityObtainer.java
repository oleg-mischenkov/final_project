package com.mischenkov.model.dao;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityObtainer<T> {

    Logger LOG = Logger.getLogger(EntityObtainer.class);

    T obtain(ResultSet rs) throws SQLException;

    static int obtainKey(ResultSet resultSet) throws SQLException {
        int key = -1;

        if ( resultSet.next() ) {
            key = resultSet.getInt(1);
        } else {
            LOG.warn("Cant obtain valid KEY");
            throw new SQLException("obtainKey(ResultSet resultSet),Cant obtain valid KEY.");
        }

        return key;
    }
}
