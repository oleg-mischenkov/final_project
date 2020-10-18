package com.mischenkov.model.dao;

import java.sql.*;

public interface CommonFunctionalityDao {

    static void updateEntityActiveState(Connection connection, int entityId, int activeState, String sql) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, activeState);
            preparedStatement.setInt(2, entityId);
            preparedStatement.executeUpdate();
        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    static void deleteEntity(Connection connection, int entityId, String sql) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entityId);
            preparedStatement.executeUpdate();
        } finally {
            Dao.quiteClose(preparedStatement);
        }
    }

    static int count(Connection con, String sql) throws SQLException {
        int result = 0;

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            resultSet.next();
            result = resultSet.getInt("count");

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(statement);
        }

        return result;
    }

}
