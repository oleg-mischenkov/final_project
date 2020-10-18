package com.mischenkov.model.dao.tariff;

import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TariffObtainer implements EntityObtainer<Tariff> {
    @Override
    public Tariff obtain(ResultSet rs) throws SQLException {
        rs.next();
        return BaseTariffObtainer.obtainEntity(rs);
    }
}
