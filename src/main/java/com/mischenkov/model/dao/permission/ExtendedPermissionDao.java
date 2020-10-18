package com.mischenkov.model.dao.permission;

import com.mischenkov.entity.Permission;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ExtendedPermissionDao extends Dao<Permission> {

    Permission getByRoleId(Connection con, int roleId) throws SQLException;
}
