package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    public void init(HttpServletRequest req, HttpServletResponse resp);

    public void make() throws IOException, ServletException;

    public void getMake() throws IOException, ServletException;

    public void postMake() throws IOException, ServletException;

}
