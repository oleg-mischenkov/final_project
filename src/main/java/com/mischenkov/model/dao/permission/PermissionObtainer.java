package com.mischenkov.model.dao.permission;

import com.mischenkov.entity.Permission;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionObtainer implements EntityObtainer<Permission> {
    @Override
    public Permission obtain(ResultSet rs) throws SQLException {
        Permission permission = new Permission();

        if (rs.next()) {
            return BasePermissionObtainer.obtainEntity(rs);
        }

        return permission;
    }
}
