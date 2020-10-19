package com.mischenkov.controller;

import com.mischenkov.entity.User;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.dbservice.MySqlUserDbService;
import com.mischenkov.model.dbservice.UserDbService;
import com.mischenkov.model.validation.ValidPattern;
import com.mischenkov.model.validation.Validator;
import com.mischenkov.session.SessionVariable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.mischenkov.model.validation.Validator.isNull;
import static com.mischenkov.model.validation.Validator.match;

/**
 *  The servlet validates and creates an account for the user.
 */
public class RegisterController extends HttpServlet {

    public static final String REQ_PARAM_LOGIN = "login";
    public static final String REQ_PARAM_FIRST_NAME = "first-name";
    public static final String REQ_PARAM_LAST_NAME = "last-name";
    public static final String REQ_PARAM_EMAIL = "email";
    public static final String REQ_PARAM_GENDER = "gender";
    public static final String REQ_PARAM_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();

        String login = req.getParameter( REQ_PARAM_LOGIN );
        String firstName = req.getParameter( REQ_PARAM_FIRST_NAME );
        String lastName = req.getParameter( REQ_PARAM_LAST_NAME );
        String email = req.getParameter( REQ_PARAM_EMAIL );
        String gender = req.getParameter( REQ_PARAM_GENDER );
        String pass = req.getParameter( REQ_PARAM_PASSWORD );

        Validator validator = new Validator();
        validator
                .append( !isNull(login) )
                .append( match( ValidPattern.LOGIN, login ) )
                .append( !isNull(firstName) )
                .append( match( ValidPattern.NAME, firstName ) )
                .append( !isNull(lastName) )
                .append( match( ValidPattern.NAME, lastName ) )
                .append( !isNull(email) )
                .append( match( ValidPattern.EMAIL, email ) )
                .append( !isNull(gender) )
                .append( match("^[mMfF]$", gender) )
                .append( !isNull(pass) )
                .append( match(ValidPattern.PASSWORD, pass) )
                .ifInvalidate( ()->{
                    req.setAttribute( REQ_PARAM_LOGIN, login );
                    req.setAttribute( REQ_PARAM_FIRST_NAME, firstName );
                    req.setAttribute( REQ_PARAM_LAST_NAME, lastName );
                    req.setAttribute( REQ_PARAM_EMAIL, email );

                    doGet(req, resp);
                } );

        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(pass);
        user.setGender(gender);

        // Save to DB
        UserDbService service = new MySqlUserDbService();
        try {
            service.saveUser(user);
        } catch (DBException e) {
            e.printStackTrace();
            httpSession.setAttribute(SessionVariable.SESSION_ERROR_MSG, "Something went wrong!");
            doGet(req, resp);
        }

        String msg = "User \"" + login + "\" has been created. You can login if you want.";
        httpSession.setAttribute(SessionVariable.SESSION_INFO_MSG, msg);

        resp.sendRedirect("success.jsp");
    }
}
