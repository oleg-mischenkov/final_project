package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.SqlOrderBy;
import com.mischenkov.model.exception.DBException;

import java.util.List;
import java.util.Map;

/**
 *  Interface for working with tariffs.
 */
public abstract class TariffDbService extends BaseDbService {

    /**
     *  The method returns the tariff that has the current localization.
     *
     * @param language - current language
     * @param tariffId - tariffs id
     * @return - tariff from the data base
     * @throws DBException
     */
    public abstract Tariff getById(Language language, int tariffId) throws DBException;

    /**
     *  The method returns a list of tariffs for the current service.
     *
     * @param language - current kanguage
     * @param serviceId - service id
     * @return - list of tariffs
     * @throws DBException
     */
    public abstract List<Tariff> getAll(Language language, int serviceId) throws DBException;

    /**
     *  The method returns a list of tariffs for the current service.
     *  The method allows you to limit the number of tariffs in the list.
     *
     * @param language  - current language
     * @param serviceId - service id
     * @param sortField - field by which you want to sort the list of tariffs
     * @param orderBy   - ASC or DESC
     * @param position  - point in the list from which to display the list
     * @param count     - number of items in the list
     * @return  - list of tariffs
     * @throws DBException
     */
    public abstract List<Tariff> getAll(Language language, int serviceId, String sortField, SqlOrderBy orderBy, int position, int count) throws DBException;

    /**
     *  The method updates the state of the Tariff.
     *
     * @param tariffId  - tariff id
     * @param active    - 1 is ON, 2 is OFF
     * @throws DBException
     */
    public abstract void updateTariffActive(int tariffId, int active) throws DBException;

    public abstract void saveTariff(Map<Integer, Tariff> langIdOnTariff, int serviceId) throws DBException;

    public abstract void delete(Tariff tariff) throws DBException;

    public abstract int count(Service service) throws DBException;
}
