package com.mischenkov.model.dao.user;

import com.mischenkov.entity.User;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserListObtainer implements EntityObtainer<List<User>> {
    @Override
    public List<User> obtain(ResultSet resultSet) throws SQLException {

        List<User> userList = new ArrayList<>();

        while ( resultSet.next() ) {
            User user = BaseUserObtainer.obtainUser(resultSet);
            userList.add(user);
        }


        return userList;
    }
}
