package com.mischenkov.controller;

import com.mischenkov.entity.Basket;
import com.mischenkov.entity.Order;
import com.mischenkov.entity.User;
import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dbservice.MySqlWalletDbService;
import com.mischenkov.model.dbservice.WalletDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.dbservice.MySqlUserDbService;
import com.mischenkov.model.dbservice.UserDbService;
import com.mischenkov.session.SessionVariable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class LoginController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private static final String REQ_PARAM_EMAIL = "email";
    private static final String REQ_PARAM_PASSWORD = "password";
    private static final String REQ_PARAM_REMEMBER_ME = "rememberMe";

    private static final String REQ_PARAM_FORM_FAIL = "fail";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object failValue = req.getAttribute(REQ_PARAM_FORM_FAIL);

        if ( failValue == null ) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {

            boolean failFlag = (boolean) failValue;

            if ( failFlag ) {
                resp.sendRedirect("index.jsp");
            } else {
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailRegexpPattern = "[\\w\\.]+@[\\w]+\\.[a-z]{3}";
        String passwordRegexpPattern = "^[\\w|_|*]{4,}$";

        String email = req.getParameter(REQ_PARAM_EMAIL);
        String pass = req.getParameter(REQ_PARAM_PASSWORD);
        // TODO delete this remember point
        String remember = req.getParameter(REQ_PARAM_REMEMBER_ME);

        boolean emailValidationFlag = Pattern.matches(emailRegexpPattern, email);
        boolean passwordValidationFlag = Pattern.matches(passwordRegexpPattern, pass);

        if (emailValidationFlag && passwordValidationFlag) {

            UserDbService dbService = new MySqlUserDbService();
            WalletDbService walletDbService = new MySqlWalletDbService();
            User user = null;
            Wallet wallet = null;
            try {
                user = dbService.getUserByEmail(email);
                wallet = walletDbService.getByUserId( user.getId() );

            } catch (DBException e) {
                LOG.warn("Cant obtain some User or Wallet object from the data base.", e);
                badForward(req, resp, email, pass);
            }

            if ( !passCheck(pass, user.getPassword()) ) {
                badForward(req, resp, email, pass);
                return;
            }

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute( SessionVariable.SESSION_USER_ATTR, user );

            req.getRequestDispatcher("current-order").include(req, resp);
            Order oldOlder = (Order) req.getAttribute(CommonControllerValues.REQ_ATTR_USER_ORDER);

            httpSession.setAttribute( SessionVariable.SESSION_WALLET_ATTR, wallet );
            httpSession.setAttribute( SessionVariable.SESSION_BASKET_ATTR, new Basket());
            httpSession.setAttribute( SessionVariable.SESSION_USER_ORDER, oldOlder );

            req.setAttribute(REQ_PARAM_FORM_FAIL, true);

            doGet(req, resp);
        } else {
            badForward(req, resp, email, pass);

        }

    }

    private boolean passCheck(String sitePass, String dbPass) {
        boolean result = false;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update( sitePass.getBytes() );
            byte[] digist = md5.digest();
            String convertPass = DatatypeConverter.printHexBinary( digist ).toLowerCase();

            if ( dbPass.equals( convertPass ) ) {
                result = true;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void badForward(HttpServletRequest req, HttpServletResponse resp, String email, String pass) throws ServletException, IOException {
        req.setAttribute(REQ_PARAM_FORM_FAIL, false);
        req.setAttribute(REQ_PARAM_EMAIL, email);
        req.setAttribute(REQ_PARAM_PASSWORD, pass);
        doGet(req, resp);
    }

}
