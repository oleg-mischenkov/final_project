package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.model.exception.DBException;

import java.util.List;

public abstract class LanguageDbService extends BaseDbService {

    /**
     *  The method returns a list of languages from the database.
     *
     * @return  - list of languages
     * @throws DBException
     */
    public abstract List<Language> getAll() throws DBException;

}
