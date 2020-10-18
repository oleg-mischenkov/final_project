package com.mischenkov.filter;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mischenkov.cookie.CookieCommon.COOKIE_LANGUAGE;
import static com.mischenkov.listener.ContextVariable.CONTEXT_INIT_DEFAULT_LANG;

public class LanguageFilter extends HttpFilter {

    private static final Logger LOG = Logger.getLogger(LanguageFilter.class);

    public static final String REQ_PARAM_LANGUAGE = "lang";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String defaultLanguage = req.getServletContext().getInitParameter(CONTEXT_INIT_DEFAULT_LANG);
        String requestLanguage = req.getParameter(REQ_PARAM_LANGUAGE);

        LOG.info("defaultLanguage = " + defaultLanguage);
        LOG.info("requestLanguage = " + requestLanguage);

        Cookie[] coocies = req.getCookies();

        if (coocies == null) {
            Cookie cookie = new Cookie(COOKIE_LANGUAGE, defaultLanguage);
            resp.addCookie(cookie);
        } else {

            Cookie langCookie = null;

            for (Cookie element: coocies) {
                if ( COOKIE_LANGUAGE.equals( element.getName() == null ? "" : element.getName() ) ) {
                    langCookie = element;
                    break;
                }
            }

            if ( langCookie == null ) {
                Cookie cookie = new Cookie(COOKIE_LANGUAGE, defaultLanguage);
                resp.addCookie(cookie);
            }

        }

        chain.doFilter(req, resp);
    }
}
