package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Permission;
import com.mischenkov.entity.User;
import com.mischenkov.model.exception.DBException;

import java.util.List;
import java.util.Map;

/**
 *  User interface.
 */
public abstract class UserDbService extends BaseDbService {

    /**
     *  The method gets the entire list of users.
     *
     * @return - list of users
     * @throws DBException
     */
    public abstract List<User> getAll() throws DBException;

    /**
     *  The method gets the user by mail name.
     *
     * @param email
     * @return - user by mail name
     * @throws DBException
     */
    public abstract User getUserByEmail(String email) throws DBException;

    /**
     *  The method saves the user to the database.
     *
     * @param user  - current user
     * @throws DBException
     */
    public abstract void saveUser(User user) throws DBException;

    /**
     *  The method allows a role for the user.
     *
     * @param userId - user id/
     * @return  - role name
     * @throws DBException
     */
    public abstract String getRoleByUserId(int userId) throws DBException;

    public abstract Permission getPermissionByUserId(int userId) throws DBException;

    public abstract int count() throws DBException;

    public abstract Map<User, String> getUserRoleNameMap(int from, int count) throws DBException;

    public abstract void updateUserActive(int userId, int active) throws DBException;
}
