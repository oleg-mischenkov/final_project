package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Permission;
import com.mischenkov.entity.User;
import com.mischenkov.model.exception.DBException;

import java.util.List;
import java.util.Map;

public abstract class UserDbService extends BaseDbService {

    public abstract List<User> getAll() throws DBException;

    public abstract User getUserByEmail(String email) throws DBException;

    public abstract void saveUser(User user) throws DBException;

    public abstract String getRoleByUserId(int userId) throws DBException;

    public abstract Permission getPermissionByUserId(int userId) throws DBException;

    public abstract int count() throws DBException;

    public abstract Map<User, String> getUserRoleNameMap(int from, int count) throws DBException;

    public abstract void updateUserActive(int userId, int active) throws DBException;
}
