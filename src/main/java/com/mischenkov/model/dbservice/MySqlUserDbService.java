package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Permission;
import com.mischenkov.entity.Role;
import com.mischenkov.entity.User;
import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.permission.ExtendedPermissionDao;
import com.mischenkov.model.dao.permission.MySqlPermissionDao;
import com.mischenkov.model.dao.role.ExtendedRoleDao;
import com.mischenkov.model.dao.role.MySqlRoleDao;
import com.mischenkov.model.dao.user.ExtendedUserDao;
import com.mischenkov.model.dao.user.MySqlUserDao;
import com.mischenkov.model.dao.wallet.ExtendedWalletDao;
import com.mischenkov.model.dao.wallet.MySqlWalletDao;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.exception.EmailOrLoginExistsDBException;
import com.mischenkov.model.exception.NotThatIdDBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySqlUserDbService extends UserDbService {

    private static final Logger LOG = Logger.getLogger(MySqlUserDbService.class);

    @Override
    public List<User> getAll() throws DBException {
        Connection connection = null;

        List<User> userList = new ArrayList<>();

        try {
            connection = getConnection();

            ExtendedUserDao userDao = new MySqlUserDao();
            userList.addAll(
                    userDao.getAll(connection)
            );

        } catch (SQLException e) {
            LOG.warn("Cant obtain a userList from the data base.", e);
            throw new DBException("Cant obtain a userList from the data base.", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return userList;
    }

    @Override
    public User getUserByEmail(String email) throws DBException {

        User user = null;

        try ( Connection connection = getConnection() ) {
            ExtendedUserDao userDao = new MySqlUserDao();
            user = userDao.getUserByEmail(connection, email);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("getUserByEmail(String email) caused an exception.", e);
        }

        return user;
    }

    @Override
    public void saveUser(User user) throws DBException {
        LOG.debug("saveUser(user) = " + user );
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedUserDao userDao = new MySqlUserDao();
            int userKey = userDao.save(connection, user);

            LOG.debug("saveUser(user) userKey = " + userKey );

            ExtendedWalletDao walletDao = new MySqlWalletDao();
            walletDao.save(connection, new Wallet(), userKey);

            connection.commit();

        } catch (SQLException e) {
            Dao.quiteRollback(connection);
            e.printStackTrace();
            throw new EmailOrLoginExistsDBException("saveUser(User user). Data Base has been this user.login [" + user.getLogin() +
                    "] or a user.email [" + user.getEmail() + "].");
        } finally {
            Dao.quiteClose(connection);
        }
    }

    @Override
    public String getRoleByUserId(int userId) throws DBException {
        String result = null;
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedUserDao userDao = new MySqlUserDao();
            result = userDao.getRoleByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotThatIdDBException("getRoleByUserId(int userId) caused an exception.", e);
        } finally {
            Dao.quiteClose(connection);
        }

        return result;
    }

    @Override
    public Permission getPermissionByUserId(int userId) throws DBException {
        Permission userPermission = null;
        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedRoleDao roleDao = new MySqlRoleDao();
            Role userRole = roleDao.getRoleByUserId(connection, userId);

            ExtendedPermissionDao permissionDao = new MySqlPermissionDao();
            int roleId = userRole.getId();
            userPermission = permissionDao.getByRoleId(connection, roleId);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("getPermissionByUserId(int userId) caused an exception.", e);
        } finally {
            Dao.quiteClose(connection);
        }

        return userPermission;
    }

    @Override
    public int count() throws DBException {
        int result = 0;

        Connection connection = null;

        try {
            connection = getConnection();
            ExtendedUserDao userDao = new MySqlUserDao();

            result = userDao.count(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("count() caused an exception.", e);
        }

        return result;
    }

    @Override
    public Map<User, String> getUserRoleNameMap(int from, int count) throws DBException {
        Map<User, String> map = null;

        Connection connection = null;

        try {
            connection = getConnection();

            ExtendedUserDao userDao = new MySqlUserDao();
            map = userDao.getUserRoleNameMap(connection, from, count);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("getUserRoleNameMap(int from, int count) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }

        return map;
    }

    @Override
    public void updateUserActive(int userId, int active) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            ExtendedUserDao userDao = new MySqlUserDao();
            userDao.updateUserActive(connection, userId, active);

            connection.commit();

        } catch (SQLException e) {
            Dao.quiteRollback(connection);
            e.printStackTrace();
            throw new DBException("updateUserActive(int userId, int active) caused an exception.", e);

        } finally {
            Dao.quiteClose(connection);
        }
    }
}
