package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.SqlOrderBy;
import com.mischenkov.model.exception.DBException;

import java.util.List;
import java.util.Map;

public abstract class TariffDbService extends BaseDbService {

    public abstract Tariff getById(Language language, int tariffId) throws DBException;

    public abstract List<Tariff> getAll(Language language, int serviceId) throws DBException;

    public abstract List<Tariff> getAll(Language language, int serviceId, String sortField, SqlOrderBy orderBy, int position, int count) throws DBException;

    public abstract void updateTariffActive(int tariffId, int active) throws DBException;

    public abstract void saveTariff(Map<Integer, Tariff> langIdOnTariff, int serviceId) throws DBException;

    public abstract void delete(Tariff tariff) throws DBException;

    public abstract int count(Service service) throws DBException;
}
