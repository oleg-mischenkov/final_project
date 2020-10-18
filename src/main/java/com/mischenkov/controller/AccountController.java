package com.mischenkov.controller;

import com.mischenkov.entity.*;
import com.mischenkov.model.dbservice.*;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.exception.WalletDoesntHaveEnoughMoneyDBException;
import com.mischenkov.model.validation.ValidPattern;
import com.mischenkov.model.validation.Validator;
import com.mischenkov.session.SessionVariable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.mischenkov.model.validation.Validator.match;

@WebServlet("/account")
public class AccountController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AccountController.class);

    // request parameters
    private static final String REQ_PARAM_CARD_NUMBER = "card";
    private static final String REQ_PARAM_DELETE_ITEM = "delete";
    private static final String REQ_PARAM_REORDER = "reorder";
    // request attributes
    public static final String REQ_ATTR_PATTERN_ERROR_MSG = CommonControllerValues.REQ_ATR_PATTERN_ERROR_MSG;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String preCardNumber = req.getParameter(REQ_PARAM_CARD_NUMBER);
        String preItemDelete = req.getParameter(REQ_PARAM_DELETE_ITEM);
        String reorder = req.getParameter(REQ_PARAM_REORDER);

        HttpSession httpSession = req.getSession();
        Order currentOrder = (Order) httpSession.getAttribute(SessionVariable.SESSION_USER_ORDER);

        if (preCardNumber != null) {

            cardValidation(preCardNumber, req, resp);

            int cardNumber = Integer.parseInt(preCardNumber);

            checkCardInDb(cardNumber, req, resp);

            cardToWalletTransaction(cardNumber, req);

            exchangeSessionWallet(req);

            httpSession.setAttribute(SessionVariable.SESSION_INFO_MSG, "Your account has been credited.");
            resp.sendRedirect("success.jsp");
        }

        if (preItemDelete != null) {
            itemValidation(preItemDelete, req, resp);
            int itemId = Integer.parseInt(preItemDelete);

            int orderId = currentOrder.getId();

            req.getRequestDispatcher("current-language").include(req, resp);
            Language language = (Language) req.getAttribute(CommonControllerValues.CURRENT_LANGUAGE);

            deleteItemFromDb(itemId, orderId, language);

            resp.sendRedirect("account");
        }

        if (reorder != null) {
            LOG.info("reorder = " + reorder );
            reorderCheck(req, resp, currentOrder);

            User user = (User) httpSession.getAttribute(SessionVariable.SESSION_USER_ATTR);

            reorderAllItems(user, req, resp);

            Wallet wallet = getRefreshWallet(user);
            httpSession.setAttribute(SessionVariable.SESSION_WALLET_ATTR, wallet);

            resp.sendRedirect("account");
        }

    }

    private Wallet getRefreshWallet(User user) throws ServletException {
        Wallet wallet = new Wallet();

        try {
            WalletDbService walletDbService = new MySqlWalletDbService();
            wallet = walletDbService.getByUserId(user.getId());
        } catch (DBException e) {
            LOG.warn("Cant refresh User wallet.", e);
            throw new ServletException("Cant refresh User wallet.", e);
        }

        return wallet;
    }

    private void reorderAllItems(User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            OrderDbService dbService = new MySqlOrderDbService();
            dbService.reorder(user);

        } catch (WalletDoesntHaveEnoughMoneyDBException e) {
            LOG.info("There is to low money");
            req.setAttribute(REQ_ATTR_PATTERN_ERROR_MSG, "You don't have enough money to complete the order.");
            doGet(req, resp);

        } catch (DBException e) {
            LOG.warn("Invalid refresh", e);
            throw new ServletException("Invalid refresh", e);
        }
    }

    private void reorderCheck(HttpServletRequest req, HttpServletResponse resp, Order currentOrder) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Wallet wallet = (Wallet) session.getAttribute(SessionVariable.SESSION_WALLET_ATTR);

        int money = wallet.getMoney();
        int orderCost = currentOrder.getTotal();

        if (orderCost > money) {
            req.setAttribute(REQ_ATTR_PATTERN_ERROR_MSG, "You don't have enough money to complete the order.");
            doGet(req, resp);
        }

    }

    private void deleteItemFromDb(int itemId, int orderId, Language currentLanguage) throws ServletException {
        try {
            OrderDbService dbService = new MySqlOrderDbService();
            dbService.deleteItem(itemId, currentLanguage, orderId);

        } catch (DBException e) {
            LOG.warn("Cant delete some Item [" + itemId + "].", e);
            throw new ServletException("Cant delete some Item [" + itemId + "].", e);
        }
    }

    private void itemValidation(String preItemDelete, HttpServletRequest req, HttpServletResponse resp) {
        Validator validator = new Validator();
        validator
                .append( match(ValidPattern.NUMBER, preItemDelete) )
                .ifInvalidate(()-> {
                    req.setAttribute(REQ_ATTR_PATTERN_ERROR_MSG, "Invalid item key: \"" + preItemDelete + '\"');
                    doGet(req, resp);
                });
    }

    private void exchangeSessionWallet(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SessionVariable.SESSION_USER_ATTR);
        Wallet wallet = null;

        try {
            WalletDbService dbService = new MySqlWalletDbService();
            wallet = dbService.getByUserId( user.getId() );

        } catch (DBException e) {
            LOG.warn("Cant obtain wallet for the User [" + user.getId() + "]", e);
            throw new ServletException("Cant obtain wallet for the User [" + user.getId() + "]", e);
        }

        session.setAttribute(SessionVariable.SESSION_WALLET_ATTR, wallet);
    }

    private void cardToWalletTransaction(int cardNumber, HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SessionVariable.SESSION_USER_ATTR);

        try {
            WalletDbService dbService = new MySqlWalletDbService();
            dbService.cardToWalletTransaction( cardNumber, user.getId() );
        } catch (DBException e) {
            LOG.warn("Could not complete the transaction for cardNumber [" + cardNumber + "] and userId [" + user.getId() + "].", e);
            throw new ServletException("Could not complete the transaction for cardNumber [" + cardNumber + "] and userId [" + user.getId() + "].", e);
        }

    }

    private void checkCardInDb(int cardNumber, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Card card = null;

        try {
            CardDbService dbService = new MySqlCardDbService();
            card = dbService.getByCode(cardNumber);

        } catch (DBException e) {
            LOG.warn("Some problem with Card obtaining", e);
            throw new ServletException(e);
        }

        LOG.debug("card = " + card);

        if ( card.getId() == 0 || card.getMoney() <= 0 ) {
            req.setAttribute(REQ_ATTR_PATTERN_ERROR_MSG, "The system does not have such a card \"" + cardNumber + '\"');
            doGet(req, resp);
        }
    }

    private void cardValidation(String preCardNumber, HttpServletRequest req, HttpServletResponse resp) {
        Validator validator = new Validator();
        validator
                .append( match(ValidPattern.CARD, preCardNumber) )
                .ifInvalidate(()-> {
                    req.setAttribute(REQ_ATTR_PATTERN_ERROR_MSG, "Invalid card: \"" + preCardNumber + '\"');
                    doGet(req, resp);
                });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();

        req.getRequestDispatcher("current-order").include(req, resp);
        Order oldOlder = (Order) req.getAttribute(CommonControllerValues.REQ_ATTR_USER_ORDER);

        httpSession.setAttribute( SessionVariable.SESSION_USER_ORDER, oldOlder );

        req.getRequestDispatcher("WEB-INF/view/account.jsp").forward(req, resp);
    }
}
