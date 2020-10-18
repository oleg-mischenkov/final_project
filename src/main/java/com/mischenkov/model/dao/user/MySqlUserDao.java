package com.mischenkov.model.dao.user;

import com.mischenkov.entity.User;
import com.mischenkov.model.dao.CommonFunctionalityDao;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.*;
import java.util.*;

public class MySqlUserDao implements ExtendedUserDao {

    // Classic CRUD queries
    private static final String SQL_SELECT_ALL_USER = "SELECT `user_id`, `gender`, `login`, `email`, `password`, `first_name`, `last_name`, `create_time`, `active` FROM `users`";

    private static final String SQL_INSERT_USER = "INSERT INTO `users` (`login`, `email`, `password`, `first_name`, `last_name`, `gender`, `active`) VALUES (?, ?, md5(?), ?, ?, ?, ?)";

    // Other queries
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT `user_id`, `gender`, `login`, `email`, `password`, `first_name`, `last_name`, `create_time`, `active` FROM `users` WHERE `email` = ?";
    private static final String SQL_SELECT_ROLE_BY_USER_ID = "SELECT `role_name` FROM `role` INNER JOIN `users` ON `users`.`role_id` = `role`.`role_id` AND `user_id` = ?";
    private static final String SQL_SELECT_USER_COUNT = "SELECT COUNT(*) as `count` FROM `users`";
    private static final String SQL_SELECT_USER_ROLE = "SELECT `user_id`, `active`, `login`, `email`, `password`, `first_name`, `last_name`, `create_time`, `gender`, `role`.`role_name` as `role` FROM `users`, `role` WHERE `users`.`role_id` = `role`.`role_id` ORDER BY `user_id` LIMIT ?, ?";
    private static final String SQL_UPDATE_USER_ACTIVE = "UPDATE `users` SET `active` = ? WHERE `user_id` = ?;";

    @Override
    public List<User> getAll(Connection con) throws SQLException {
        Objects.requireNonNull(con, "getAll(Connection con), \"con\" is null.");

        List<User> userList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_USER);

            EntityObtainer<List<User>> obtainer = new UserListObtainer();
            userList = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(statement);
        }

        return userList;
    }

    @Override
    public User getById(Connection con, User entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, User entity) throws SQLException {
        Objects.requireNonNull(con, "save(Connection con, User entity), \"con\" is null.");
        Objects.requireNonNull(entity, "save(Connection con, User entity), \"entity\" is null.");

        int userId = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            preparedStatement.setString(k++, entity.getLogin());
            preparedStatement.setString(k++, entity.getEmail());
            preparedStatement.setString(k++, entity.getPassword());
            preparedStatement.setString(k++, entity.getFirstName());
            preparedStatement.setString(k++, entity.getLastName());
            preparedStatement.setString(k++, entity.getGender().name());
            preparedStatement.setInt(k++, entity.isActive() ? 1 : 0);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            userId = EntityObtainer.obtainKey(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return userId;
    }

    @Override
    public void delete(Connection con, User entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count(Connection con) throws SQLException {
        Objects.requireNonNull(con, "count(Connection con), \"con\" is null.");

        return CommonFunctionalityDao.count(con, SQL_SELECT_USER_COUNT);
    }

    @Override
    public User getUserByEmail(Connection con, String email) throws SQLException {
        Objects.requireNonNull(con, "getUserByEmail(Connection con, String email), \"con\" is null.");
        Objects.requireNonNull(email, "getUserByEmail(Connection con, String email), \"email\" is null");

        User user = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement( SQL_SELECT_USER_BY_EMAIL );
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<User> obtainer = new UserObtainer();
            user = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return user;
    }

    @Override
    public String getRoleByUserId(Connection con, int userId) throws SQLException {
        Objects.requireNonNull(con, "getRoleByUserId(Connection con, int userId), \"con\" is null.");

        String result = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_ROLE_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString("role_name");
            }

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        if (result == null) {
            result = "";
        }
        return result;
    }

    @Override
    public Map<User, String> getUserRoleNameMap(Connection con, int from, int count) throws SQLException {
        Objects.requireNonNull(con, "getUserRoleNameMap(Connection con, int from, int count), \"con\" is null.");
        Map<User, String> userRoleMap = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_USER_ROLE);
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, count);

            resultSet = preparedStatement.executeQuery();
            EntityObtainer<Map<User, String>> obtainer = new UserRoleNameObtainer();

            userRoleMap = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return userRoleMap;
    }

    @Override
    public void updateUserActive(Connection con, int userId, int active) throws SQLException {
        Objects.requireNonNull(con, "updateUserActive(Connection con, int userId, int active), \"con\" is null.");

        CommonFunctionalityDao.updateEntityActiveState(con, userId, active, SQL_UPDATE_USER_ACTIVE);
    }

}