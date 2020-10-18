package com.mischenkov.model.dao.permission;

import com.mischenkov.entity.Permission;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BasePermissionObtainer extends EntityObtainer<Permission> {

    static Permission obtainEntity(ResultSet resultSet) throws SQLException {
        Permission permission = new Permission();

        int id = resultSet.getInt(1);
        permission.setId(id);

        int adminAccess = resultSet.getInt(2);
        permission.setAdminAccess(adminAccess);

        int cardAccess = resultSet.getInt(3);
        permission.setCardAccess(cardAccess);

        return permission;
    }

}
