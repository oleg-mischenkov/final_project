package com.mischenkov.controller;

import com.mischenkov.entity.Basket;
import com.mischenkov.entity.Order;
import com.mischenkov.entity.User;
import com.mischenkov.entity.Wallet;
import com.mischenkov.model.dbservice.MySqlOrderDbService;
import com.mischenkov.model.dbservice.MySqlWalletDbService;
import com.mischenkov.model.dbservice.OrderDbService;
import com.mischenkov.model.dbservice.WalletDbService;
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
 *  Servlet that processes the shopping cart.
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CartController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Wallet wallet = (Wallet) session.getAttribute(SessionVariable.SESSION_WALLET_ATTR);
        Basket basket = (Basket) session.getAttribute(SessionVariable.SESSION_BASKET_ATTR);
        User user = (User) session.getAttribute(SessionVariable.SESSION_USER_ATTR);

        if ( wallet.getMoney() < basket.totalMoney() ) {
            doGet(req, resp);
        }

        Order order = new Order();
        order.setItemList( basket.getAll() );
        order.setTotal( basket.totalMoneyPerWeek(4) );

        saveOrderToDb(order, user);
        wallet = getWalletFromDb(user);

        session.setAttribute(SessionVariable.SESSION_BASKET_ATTR, new Basket());
        session.setAttribute(SessionVariable.SESSION_WALLET_ATTR, wallet);

        resp.sendRedirect("account");
    }

    private Wallet getWalletFromDb(User user) throws ServletException {
        Wallet wallet = null;

        try {
            WalletDbService dbService = new MySqlWalletDbService();
            wallet = dbService.getByUserId( user.getId() );
        } catch (DBException e) {
            LOG.warn("Cant obtain a User id[" + user.getId() + "] from the data base.", e);
            throw new ServletException("Cant obtain a User id[" + user.getId() + "] from the data base.", e);
        }

        return wallet;
    }

    private void saveOrderToDb(Order order, User user) throws ServletException {

        try {
            OrderDbService dbService = new MySqlOrderDbService();
            dbService.saveOrder(order, user);

        } catch (DBException e) {
            LOG.warn("Cant save Order to the data base", e);
            throw new ServletException("Cant save Order to the data base", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req, resp);
    }
}
