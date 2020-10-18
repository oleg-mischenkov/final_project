package com.mischenkov.model.dao.tariff;

import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseTariffObtainer extends EntityObtainer<Tariff> {
    static Tariff obtainEntity(ResultSet resultSet) throws SQLException {
        Tariff tariff = new Tariff();

        int id = resultSet.getInt("tariff_id");
        tariff.setId(id);

        int active = resultSet.getInt("active");
        tariff.setActive(active);

        int price = resultSet.getInt("price");
        tariff.setPrice(price);

        String startDate = resultSet.getString("start_date");
        tariff.setStartDate(startDate);

        String endDate = resultSet.getString("end_date");
        tariff.setEndDate(endDate);

        String title = resultSet.getString("title");
        tariff.setTitle(title);

        String description = resultSet.getString("description");
        tariff.setDescription(description);

        String shortDescription = resultSet.getString("short_description");
        tariff.setShortDescription(shortDescription);

        return tariff;
    }
}
