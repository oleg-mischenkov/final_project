package com.mischenkov.model.dao.permission;

import com.mischenkov.entity.Permission;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MySqlPermissionDao implements ExtendedPermissionDao {

    private static final String SQL_SELECT_PERM_BY_ROLE = "SELECT `perm_id`, `admin_access`, `card_access` FROM `role_permission` WHERE `role_id` = ?";

    @Override
    public List<Permission> getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Permission getById(Connection con, Permission entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, Permission entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Permission entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Permission getByRoleId(Connection con, int roleId) throws SQLException {
        Objects.requireNonNull(con, "getByRoleId(Connection con, int roleId), \"con\" is null");

        Permission result = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_PERM_BY_ROLE);
            preparedStatement.setInt(1, roleId);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<Permission> obtainer = new PermissionObtainer();
            result = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return result;
    }
}
