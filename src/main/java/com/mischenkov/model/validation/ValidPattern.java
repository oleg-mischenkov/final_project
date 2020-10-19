package com.mischenkov.model.validation;

/**
 *  Common regexp patterns for validation of data from the site.
 */
public interface ValidPattern {

    String LOGIN = "[a-zA-Z0-9а-яА-ЯжЖёЁ]{3,16}";
    String NAME = "[a-zA-Zа-яА-ЯжЖёЁ]{3,16}";
    String PASSWORD = "^[\\w|_|*]{4,}$";
    String EMAIL = "[\\w\\.]+@[\\w]+\\.[a-z]{3}";
    String DATE = "[\\d]{4}-[\\d]{2}-[\\d]{2}";
    String PRICE_DECIMAL = "^[\\d]{1,}.[\\d]{2}$";
    String NUMBER = "^[\\d]{1,}$";
    String CARD = "^[\\d]{6}$";
}
