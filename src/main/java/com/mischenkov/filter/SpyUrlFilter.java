package com.mischenkov.filter;

import com.mischenkov.session.MemoryUrlHttpSession;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SpyUrlFilter extends HttpFilter {

    private static final Logger LOG = Logger.getLogger(SpyUrlFilter.class);

    private static final String METHOD_POST = "POST";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOG.info("doFilter(req, resp, chain)");
        String servletPath = req.getServletPath();
        String quaryString = req.getQueryString();
        String contextPath = req.getContextPath();
        String url = servletPath;

        if (quaryString != null) {
            url += "?" + quaryString;
        }

        url = contextPath + url;

        if (!req.getMethod().equals(METHOD_POST) && !url.contains(".css")) {
            HttpSession session = req.getSession();
            MemoryUrlHttpSession memoryUrlHttpSession = new MemoryUrlHttpSession(session);
            memoryUrlHttpSession.putUrl(url);
        }


        chain.doFilter(req, resp);
    }
}
