package com.mischenkov.controller;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Order;
import com.mischenkov.entity.User;
import com.mischenkov.model.dbservice.MySqlOrderDbService;
import com.mischenkov.model.dbservice.OrderDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.session.SessionVariable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *  The servlet receives the current status of the order.
 */
@WebServlet("/current-order")
public class CurrentOrderController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CurrentOrderController.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(SessionVariable.SESSION_USER_ATTR);
        Order order = new Order();

        if (user != null) {

            req.getRequestDispatcher("current-language").include(req, resp);
            Language language = (Language) req.getAttribute(CommonControllerValues.CURRENT_LANGUAGE);

            order = getOrderFromDb(user, language);

        }


        req.setAttribute(CommonControllerValues.REQ_ATTR_USER_ORDER, order);
    }

    private Order getOrderFromDb(User user, Language language) throws ServletException {
        Order order = null;

        try {
            OrderDbService dbService = new MySqlOrderDbService();
            order = dbService.getOrder(user.getId(), language);

        } catch (DBException e) {
            LOG.warn("Cant obtain an Order for User", e);
            throw new ServletException("Cant obtain an Order for User", e);
        }

        return order;
    }
}
