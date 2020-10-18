package com.mischenkov.model.dao.user;

import com.mischenkov.entity.User;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface ExtendedUserDao extends Dao<User> {

    User getUserByEmail(Connection con, String email) throws SQLException;

    String getRoleByUserId(Connection con, int userId) throws SQLException;

    Map<User, String> getUserRoleNameMap(Connection con, int from, int count) throws SQLException;

    void updateUserActive(Connection con, int userId, int active) throws SQLException;

}
