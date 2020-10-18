package com.mischenkov.model.dao.role;

import com.mischenkov.entity.Role;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MySqlRoleDao implements ExtendedRoleDao {

    private static final String SQL_SELECT_ROLR_BY_USER_ID = "SELECT `users`.`role_id`, `role_name`, `special` FROM `role` \n" +
            "\tINNER JOIN `users` ON `users`.`role_id` = `role`.`role_id` AND `user_id` = ?";

    @Override
    public List<Role> getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role getById(Connection con, Role entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, Role entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Role entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role getRoleByUserId(Connection con, int userId) throws SQLException {
        Objects.requireNonNull(con, "getRoleByUserId(Connection con, int userId), \"con\" is null.");
        Role result = null;

        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            prepStatement = con.prepareStatement(SQL_SELECT_ROLR_BY_USER_ID);
            prepStatement.setInt(1, userId);

            resultSet = prepStatement.executeQuery();

            EntityObtainer<Role> obtainer = new RoleObtainer();
            result = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(prepStatement);
        }

        return result;
    }
}
