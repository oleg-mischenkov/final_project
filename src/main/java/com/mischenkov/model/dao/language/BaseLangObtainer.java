package com.mischenkov.model.dao.language;

import com.mischenkov.entity.Language;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseLangObtainer extends EntityObtainer<Language> {

    static Language obtainLang(ResultSet resultSet) throws SQLException {
        Language language = new Language();

        int id = resultSet.getInt("lang_id");
        language.setId(id);

        String title = resultSet.getString("title");
        language.setTitle(title);

        int active = resultSet.getInt("active");
        language.setActive(active);

        String iso_1 = resultSet.getString("iso-639-1");
        language.setIso639_1(iso_1);

        String iso_2 = resultSet.getString("iso-639-2");
        language.setIso639_2(iso_2);

        String iso_3 = resultSet.getString("iso-639-3");
        language.setIso639_2(iso_3);

        int code = resultSet.getInt("code");
        language.setCode(code);

        return language;
    }

}
