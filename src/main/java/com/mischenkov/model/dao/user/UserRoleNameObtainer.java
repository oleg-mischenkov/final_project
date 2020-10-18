package com.mischenkov.model.dao.user;

import com.mischenkov.entity.User;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserRoleNameObtainer implements EntityObtainer<Map<User, String>> {
    @Override
    public Map<User, String> obtain(ResultSet rs) throws SQLException {
        Map<User, String> map = new LinkedHashMap<>();

        while (rs.next()) {
            User user = BaseUserObtainer.obtainUser(rs);
            String roleName = rs.getString("role");

            map.put(user, roleName);
        }

        return map;
    }
}
