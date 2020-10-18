package com.mischenkov.model.dao.role;

import com.mischenkov.entity.Role;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleObtainer implements EntityObtainer<Role> {
    @Override
    public Role obtain(ResultSet rs) throws SQLException {
        Role role = new Role();

        rs.next();

        int id = rs.getInt("role_id");
        role.setId(id);
        String name = rs.getString("role_name");
        role.setName(name);
        int special = rs.getInt("special");
        role.setSpecial(special);

        return role;
    }
}
