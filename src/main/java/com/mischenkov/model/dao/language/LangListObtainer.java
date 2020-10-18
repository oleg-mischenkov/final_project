package com.mischenkov.model.dao.language;

import com.mischenkov.entity.Language;
import com.mischenkov.model.dao.EntityObtainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LangListObtainer implements EntityObtainer<List<Language>> {
    @Override
    public List<Language> obtain(ResultSet rs) throws SQLException {
        List<Language> languageList = new ArrayList<>();

        while (rs.next()) {
            Language language = BaseLangObtainer.obtainLang(rs);
            languageList.add(language);
        }
        return languageList;
    }
}
