package com.mischenkov.controller.admin;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  The class is the base implementation of the front controller pattern.
 */
public abstract class AdminAbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AdminAbstractCommand.class);

    private static final String METHOD_GET = "GET";

    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void make() throws IOException, ServletException {
        String requestMethod = req.getMethod();

        if ( requestMethod.equals(METHOD_GET) ) {
            getMake();
        } else {
            postMake();
        }
    }

    @Override
    public void getMake() throws IOException, ServletException {
        // NOP
    }

    @Override
    public void postMake() throws IOException, ServletException {
        // NOP
    }

    /**
     *  This method finds the desired view for the handler.
     *
     * @param urlGoal   - name of the jsp page
     * @throws IOException
     * @throws ServletException
     */
    protected void forward(String urlGoal) throws IOException, ServletException {
        urlGoal = String.format("/WEB-INF/view/admin/%s.jsp", urlGoal);
        req.getRequestDispatcher( urlGoal ).forward(req, resp);
    }
}
