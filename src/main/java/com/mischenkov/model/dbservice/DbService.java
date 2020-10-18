package com.mischenkov.model.dbservice;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbService {

    Connection getConnection() throws SQLException;

}
