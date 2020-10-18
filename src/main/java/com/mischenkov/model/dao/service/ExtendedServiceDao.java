package com.mischenkov.model.dao.service;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.model.dao.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ExtendedServiceDao extends Dao<Service> {

    List<Service> getAll(Connection con, Language language) throws SQLException;

    void updateServiceActive(Connection con, int serviceId, int active) throws SQLException;

    void saveService(Connection con, Map<Integer, Service> langIdOnTariff) throws SQLException;

    Service getById(Connection con, Service entity, Language language) throws SQLException;
}
