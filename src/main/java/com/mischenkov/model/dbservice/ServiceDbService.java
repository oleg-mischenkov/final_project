package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.model.exception.DBException;

import java.util.List;
import java.util.Map;

public abstract class ServiceDbService extends BaseDbService {

    public abstract List<Service> getAll(Language language) throws DBException;

    public abstract void updateServiceActive(int serviceId, int active) throws DBException;

    public abstract void saveService(Map<Integer, Service> langIdOnService) throws DBException;

    public abstract void delete(Service service) throws DBException;

    public abstract Service getById(Service entity, Language language) throws DBException;

}
