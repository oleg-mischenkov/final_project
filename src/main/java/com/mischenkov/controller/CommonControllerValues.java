package com.mischenkov.controller;

public interface CommonControllerValues {

    // request parameters
    String REQ_PARAM_COUNT = "count";           // for pagination
    String REQ_PARAM_POSITION = "position";     // for pagination

    // request attributes
    String REQ_ATR_PATTERN_ERROR_MSG = "patternErrorMsg";
    String REQ_ATR_PATTERN_ERROR_FIELD = "errorField";
    String REQ_ATR_PAGINATION_URLS = "paginationUrls";
    String REQ_ATTR_USER_ORDER = "userOrder";

    // common variables
    String CURRENT_LANGUAGE = "currentLanguage";
    String CURRENT_ORDER = "currentOrder";
    int DEFAULT_ROW_COUNT = 10;                  // for pagination
    int DEFAULT_START_POSITION = 0;              // for pagination

}
