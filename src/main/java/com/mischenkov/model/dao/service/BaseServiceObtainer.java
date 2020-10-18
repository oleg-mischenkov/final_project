package com.mischenkov.model.dao.service;

import com.mischenkov.entity.Service;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseServiceObtainer extends EntityObtainer<Service> {

    static Service obtainService(ResultSet resultSet) throws SQLException {
        Service service = new Service();

        int id = resultSet.getInt("service_id");
        service.setId(id);

        int active = resultSet.getInt("active");
        service.setActive(active);


        String endDate = resultSet.getString("end_date");
        service.setEndDate(endDate);

        String title = resultSet.getString("title");
        service.setTitle(title);

        String startDate = resultSet.getString("start_date");
        service.setStartDate(startDate);

        String shortDescription = resultSet.getString("short_description");
        service.setShortDescription(shortDescription);

        String description = resultSet.getString("description");
        service.setDescription(description);


        return service;
    }

}
