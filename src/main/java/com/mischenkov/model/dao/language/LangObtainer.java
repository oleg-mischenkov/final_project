package com.mischenkov.model.dao.language;

import com.mischenkov.entity.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LangObtainer implements BaseLangObtainer {
    @Override
    public Language obtain(ResultSet rs) throws SQLException {
        Language language = new Language();

        int rsCount = 0;
        while ( rs.next() ) {
            rsCount++;

            language = BaseLangObtainer.obtainLang(rs);
        }

        if (rsCount != 1) {
            throw new SQLException("obtain(ResultSet resultSet), \"resultSet\" contains [" + rsCount + "] values but should be 1.");
        }

        return language;
    }
}
