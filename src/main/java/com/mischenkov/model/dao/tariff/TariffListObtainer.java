package com.mischenkov.model.dao.tariff;

import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TariffListObtainer implements EntityObtainer<List<Tariff>> {
    @Override
    public List<Tariff> obtain(ResultSet rs) throws SQLException {
        List<Tariff> tariffList = new ArrayList<>();

        while (rs.next()) {
            Tariff tariff = BaseTariffObtainer.obtainEntity(rs);
            tariffList.add(tariff);
        }

        return tariffList;
    }
}
