package com.mischenkov.model.dao.language;

import com.mischenkov.entity.Language;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MySqlLangDao implements ExtendedLangDao<Language> {

    private static final String SQL_SELECT_ALL_LANGUAGE ="SELECT `lang_id`, `title`, `active`, `iso-639-1`, `iso-639-2`, `iso-639-3`, `code` from `languages`";

    @Override
    public List<Language> getAll(Connection con) throws SQLException {
        Objects.requireNonNull(con, "getAll(Connection con), \"con\" is null.");

        List<Language> languageList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_LANGUAGE);

            EntityObtainer<List<Language>> obtainer = new LangListObtainer();
            languageList = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(statement);
        }

        return languageList;
    }

    @Override
    public Language getById(Connection con, Language entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, Language entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Language entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
