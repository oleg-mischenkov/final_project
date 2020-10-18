package com.mischenkov.model.dao.user;

import com.mischenkov.entity.User;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseUserObtainer extends EntityObtainer<User> {

    static User obtainUser(ResultSet resultSet) throws SQLException {

        User user = new User();

        int id = resultSet.getInt("user_id");
        user.setId(id);

        String login = resultSet.getString("login");
        user.setLogin(login);

        String email = resultSet.getString("email");
        user.setEmail(email);

        String pass = resultSet.getString("password");
        user.setPassword(pass);

        String firstName = resultSet.getString("first_name");
        user.setFirstName(firstName);

        String lastName = resultSet.getString("last_name");
        user.setLastName(lastName);

        String date = resultSet.getString("create_time");
        user.setCreateTime(date);

        String gender = resultSet.getString("gender");
        user.setGender(gender);

        int active = resultSet.getInt("active");
        user.setActive(active);

        return user;
    }

}
