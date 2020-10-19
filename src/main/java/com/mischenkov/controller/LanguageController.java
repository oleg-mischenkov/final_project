package com.mischenkov.controller;

import com.mischenkov.session.MemoryUrlHttpSession;
import com.mischenkov.session.SessionVariable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.mischenkov.cookie.CookieCommon.COOKIE_LANGUAGE;

/**
 *  Servlet works with site languages.
 */
public class LanguageController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LanguageController.class);

    public static final String REQ_PARAM_LANGUAGE = "lang";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String language = req.getParameter(REQ_PARAM_LANGUAGE);

        LOG.info("language = " + language);

        String fromUrl = req.getHttpServletMapping().getServletName();

        Cookie cookie = new Cookie(COOKIE_LANGUAGE, language);

        resp.addCookie(cookie);

        MemoryUrlHttpSession urlHttpSession = new MemoryUrlHttpSession(req.getSession());

        List<String> urls = (List<String>) urlHttpSession.getAttribute(SessionVariable.SESSION_URL_SCOPE);

        LOG.info(urls);

        resp.sendRedirect( urlHttpSession.getUrl() );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
