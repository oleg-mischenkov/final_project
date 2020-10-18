package com.mischenkov.model.dao.tariff;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.SqlOrderBy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ExtendedTariffDao extends Dao<Tariff> {

    Tariff getById(Connection con, Language language, int tariffId) throws SQLException;

    List<Tariff> getAll(Connection con, Language language, int serviceId) throws SQLException;

    List<Tariff> getAll(Connection con, Language language, int serviceId, String sortField, SqlOrderBy orderBy, int position, int count) throws SQLException;

    void updateTariffActive(Connection con, int tariffId, int active) throws SQLException;

    void saveTariff(Connection con, Map<Integer, Tariff> langIdOnService, int serviceId) throws SQLException;

    int count(Connection con, Service service) throws SQLException;

}
