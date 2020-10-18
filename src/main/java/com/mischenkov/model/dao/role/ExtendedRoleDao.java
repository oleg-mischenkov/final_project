package com.mischenkov.model.dao.role;

import com.mischenkov.entity.Role;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ExtendedRoleDao extends Dao<Role> {

    Role getRoleByUserId(Connection con, int userId) throws SQLException;

}
