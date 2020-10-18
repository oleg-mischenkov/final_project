package com.mischenkov.model.dbservice;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDbService implements DbService {
    static {
        try {
            Context initialContext = new InitialContext();
            Context tc = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) tc.lookup("jdbc/ST_FOR_FINAL_PROJECT");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static DataSource dataSource;
}
