package com.mischenkov.model.dao.order;

import com.mischenkov.entity.Order;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderObtainer implements EntityObtainer<Order> {
    @Override
    public Order obtain(ResultSet rs) throws SQLException {
        Order order = new Order();

        if (rs.next()) {
            return order = BaseOrderObtainer.obtainEntity(rs);
        }

        return order;
    }
}
