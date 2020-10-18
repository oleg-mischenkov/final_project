package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Language;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.language.MySqlLangDao;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlLanguageDbService extends LanguageDbService {

    private static final Logger LOG = Logger.getLogger(MySqlLanguageDbService.class);

    @Override
    public List<Language> getAll() throws DBException {
        List<Language> result = new ArrayList<>();

        Connection connection = null;

        try {
            connection = getConnection();

            Dao<Language> dao = new MySqlLangDao();
            List<Language> languageList = dao.getAll(connection);

            result.addAll(languageList);

        } catch (SQLException e) {
            LOG.warn("Cannot obtain the data source", e);
            throw new DBException("List<Language> getAll() caused an exception");
        } finally {
            Dao.quiteClose(connection);
        }

        return result;
    }
}
