package com.mischenkov.controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  The servlet processes the 500 page of the site.
 */
public class ExceptionController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ExceptionController.class);

    public static final String REQ_PARAM_EXCEPTION_STACK_TRACE = "stackTrace";
    private static final String REQ_PARAM_DB_EXCEPTION = "We have some problem with our data base";
    private static final String REQ_PARAM_COMMON_EXCEPTION = "We have some problem with our site";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("500 page");
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        LOG.warn("Exception is: ", throwable);

        if (throwable.getClass().getSimpleName().equals("DBException")) {
            req.setAttribute(REQ_PARAM_EXCEPTION_STACK_TRACE, REQ_PARAM_DB_EXCEPTION);
        } else {
            req.setAttribute(REQ_PARAM_EXCEPTION_STACK_TRACE, REQ_PARAM_COMMON_EXCEPTION);
        }

        req.getRequestDispatcher("WEB-INF/view/exception.jsp").forward(req, resp);

    }
}
