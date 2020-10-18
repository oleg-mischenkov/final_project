package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFrontController extends HttpServlet {

    private static final String ADMIN_CLASS_PATH_PATTERN = "com.mischenkov.controller.admin.%sCommand";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = getCommand(req);
        command.init(req, resp);

        resp.addHeader("Content-Encoding", "identity");
        resp.addHeader("Connection", "close");

        command.make();
    }

    private Command getCommand(HttpServletRequest req) {
        String requestCommand = req.getParameter("command");
        requestCommand = firstLetterUp( requestCommand );
        String commandClassPath = String.format( ADMIN_CLASS_PATH_PATTERN, requestCommand );

        try {
            Class commandClass = Class.forName( commandClassPath );
            Command command = (Command) commandClass.asSubclass( Command.class ).newInstance();
            return command;

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorCommand();
        }
    }

    private static String firstLetterUp(String command) {

        if (command != null && command.length() > 1) {
            char[] letters = command.toCharArray();
            letters[0] = Character.toUpperCase( letters[0] );

            command = new String(letters);
        }

        return command;
    }

}
