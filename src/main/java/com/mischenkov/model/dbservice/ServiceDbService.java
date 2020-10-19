package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.model.exception.DBException;

import java.util.List;
import java.util.Map;

/**
 *  Service interface.
 */
public abstract class ServiceDbService extends BaseDbService {

    /**
     *  The method receives all services with the current language.
     *
     * @param language - current language
     * @return - list of services
     * @throws DBException
     */
    public abstract List<Service> getAll(Language language) throws DBException;

    /**
     *  The method updates the state of the service.
     *
     * @param serviceId
     * @param active - 1 is ON, 2 is OFF
     * @throws DBException
     */
    public abstract void updateServiceActive(int serviceId, int active) throws DBException;

    /**
     *  The method saves a list of services.
     *
     * @param langIdOnService - The first parameter in the card is the language id, the second parameter is the service.
     * @throws DBException
     */
    public abstract void saveService(Map<Integer, Service> langIdOnService) throws DBException;

    /**
     *  The method removes the service.
     *
     * @param service
     * @throws DBException
     */
    public abstract void delete(Service service) throws DBException;

    public abstract Service getById(Service entity, Language language) throws DBException;

}
