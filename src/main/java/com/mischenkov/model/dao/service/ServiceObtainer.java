package com.mischenkov.model.dao.service;

import com.mischenkov.entity.Service;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceObtainer implements EntityObtainer<Service> {
    @Override
    public Service obtain(ResultSet rs) throws SQLException {
        Service service = new Service();

        rs.next();
        service = BaseServiceObtainer.obtainService(rs);

        return service;
    }
}
