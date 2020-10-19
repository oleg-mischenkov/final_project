package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Basic interface for forming an incoming request handler.
 */
public interface Command {

    /**
     *  The method initializes the concrete handler implementation as.
     *
     * @param req   - request parameter from servlet
     * @param resp  - response parameter from servlet
     */
    void init(HttpServletRequest req, HttpServletResponse resp);

    /**
     *  The general method that does the job.
     *
     * @throws IOException
     * @throws ServletException
     */
    void make() throws IOException, ServletException;

    /**
     *  Method for processing a GET request.
     *
     * @throws IOException
     * @throws ServletException
     */
    void getMake() throws IOException, ServletException;

    /**
     *  Method for processing a POST request.
     *
     * @throws IOException
     * @throws ServletException
     */
    void postMake() throws IOException, ServletException;

}
