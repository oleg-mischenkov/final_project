package com.mischenkov.model.dbservice;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbService {

    /**
     * The method returns the same connection to the database
     *
     * @return  - database connection
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

}
