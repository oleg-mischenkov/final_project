package com.mischenkov.model.dao.order;

import com.mischenkov.entity.Order;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseOrderObtainer extends EntityObtainer<Order> {

    static Order obtainEntity(ResultSet resultSet) throws SQLException {
        Order order = new Order();

        int id = resultSet.getInt("order_id");
        order.setId(id);

        int total = resultSet.getInt("total");
        order.setTotal(total);

        return order;
    }

}
