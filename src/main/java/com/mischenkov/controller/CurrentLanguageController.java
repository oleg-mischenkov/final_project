package com.mischenkov.controller;

import com.mischenkov.cookie.CookieCommon;
import com.mischenkov.entity.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.mischenkov.cookie.CookieCommon.COOKIE_LANGUAGE;
import static com.mischenkov.listener.ContextVariable.CONTEXT_CURRENT_LANGS;
import static com.mischenkov.listener.ContextVariable.CONTEXT_INIT_DEFAULT_LANG;

@WebServlet("/current-language")
public class CurrentLanguageController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CurrentLanguageController.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("service(req, resp)");

        String currentLanguageName = CookieCommon.getParameter( COOKIE_LANGUAGE, req );

        if (currentLanguageName == null) {
            currentLanguageName = (String) req.getServletContext().getAttribute( CONTEXT_INIT_DEFAULT_LANG );
            LOG.debug("currentLanguageName is null, obtain default language.");
        }

        LOG.debug("currentLanguageName = " + currentLanguageName);

        Language language = getLanguageFromContext( currentLanguageName, req );

        req.setAttribute(CommonControllerValues.CURRENT_LANGUAGE, language);
    }

    private Language getLanguageFromContext(String languageName, HttpServletRequest req) throws ServletException {
        Language language = null;
        List<Language> languageList = (List<Language>) req.getServletContext().getAttribute( CONTEXT_CURRENT_LANGS );

        LOG.debug("languageList = " + languageList);

        for (Language element: languageList) {
            String name = element.getIso639_1();
            LOG.debug("lang = " + name);
            if ( name.equals(languageName) ) {
                language = element;
                break;
            }
        }

        if ( language == null ) {
            LOG.warn("system does not have the desired language");
            throw new ServletException("system does not have the desired language");
        }

        return language;
    }

}
