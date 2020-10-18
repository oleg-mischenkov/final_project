package com.mischenkov.model.dao.service;

import com.mischenkov.entity.Service;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceListObtainer implements EntityObtainer<List<Service>> {
    @Override
    public List<Service> obtain(ResultSet rs) throws SQLException {
        List<Service> serviceList = new ArrayList<>();

        while (rs.next()) {
            Service service = BaseServiceObtainer.obtainService(rs);
            serviceList.add(service);
        }

        return serviceList;
    }
}
