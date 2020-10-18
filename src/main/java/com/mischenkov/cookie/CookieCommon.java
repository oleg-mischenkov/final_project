package com.mischenkov.cookie;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public interface CookieCommon {

    Logger LOG = Logger.getLogger(CookieCommon.class);

    String COOKIE_LANGUAGE = "user-language";

    static String getParameter(String parameterName, HttpServletRequest request) {
        Objects.requireNonNull(parameterName, "getParameter(String parameterName, HttpServletRequest request), \"parameterName\" is null.");

        String result = null;

        Cookie[] cookie = request.getCookies();

        if (cookie != null) {
            for (Cookie element: cookie) {
                String cookieName = element.getName();
                if ( parameterName.equals(cookieName) ) {
                    result = element.getValue();
                    break;
                }
            }
        }

        return result;
    }

}
