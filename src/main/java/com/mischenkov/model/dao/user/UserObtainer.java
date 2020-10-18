package com.mischenkov.model.dao.user;

import com.mischenkov.entity.User;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserObtainer implements EntityObtainer<User> {
    @Override
    public User obtain(ResultSet resultSet) throws SQLException {
        User user = new User();

        int rsCount = 0;
        while ( resultSet.next() ) {
            rsCount++;

            user = BaseUserObtainer.obtainUser(resultSet);
        }

        if (rsCount != 1) {
            throw new SQLException("obtain(ResultSet resultSet), \"resultSet\" contains [" + rsCount + "] values but should be 1.");
        }

        return user;
    }
}
