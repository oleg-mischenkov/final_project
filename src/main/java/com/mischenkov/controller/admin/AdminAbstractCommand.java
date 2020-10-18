package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AdminAbstractCommand implements Command {

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

    protected void forward(String urlGoal) throws IOException, ServletException {
        urlGoal = String.format("/WEB-INF/view/admin/%s.jsp", urlGoal);
        req.getRequestDispatcher( urlGoal ).forward(req, resp);
    }
}
