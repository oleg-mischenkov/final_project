package com.mischenkov.controller;

import com.mischenkov.session.MemoryUrlHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ErrorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MemoryUrlHttpSession memoryUrlHttpSession = new MemoryUrlHttpSession(session);

        String requestUrl = memoryUrlHttpSession.getUrl();
        req.setAttribute("urlPage", requestUrl);

        req.getRequestDispatcher("WEB-INF/view/404-error-page.jsp").forward(req, resp);
    }
}
