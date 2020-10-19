package com.mischenkov.controller;

import com.mischenkov.entity.Basket;
import com.mischenkov.entity.Language;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dbservice.MySqlTariffDbService;
import com.mischenkov.model.dbservice.TariffDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.session.MemoryUrlHttpSession;
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
@WebServlet("/basked")
public class BaskedController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(BaskedController.class);

    private static final String REQ_PARAM_TARIFF = "tariff";
    private static final String REQ_PARAM_DELETE = "delete";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String preTariffId = req.getParameter(REQ_PARAM_TARIFF);
        String preDeleteId = req.getParameter(REQ_PARAM_DELETE);

        HttpSession session = req.getSession();

        if (preTariffId != null) {

            int tariffId = Integer.parseInt(preTariffId);

            Tariff tariff = obtainTariffFromDb(tariffId, req, resp);

            Basket basket = (Basket) session.getAttribute( SessionVariable.SESSION_BASKET_ATTR );

            basket.add(tariff);
            session.setAttribute( SessionVariable.SESSION_BASKET_ATTR, basket );
        }

        if (preDeleteId != null) {

            int deleteId = Integer.parseInt(preDeleteId);

            Basket basket = (Basket) session.getAttribute( SessionVariable.SESSION_BASKET_ATTR );
            basket.deleteId(deleteId);

            session.setAttribute( SessionVariable.SESSION_BASKET_ATTR, basket );
        }

        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MemoryUrlHttpSession httpSession = new MemoryUrlHttpSession(session);

        resp.sendRedirect( httpSession.getUrl() );
    }

    private Tariff obtainTariffFromDb(int tariffId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("current-language").include(req, resp);
        Language language = (Language) req.getAttribute(CommonControllerValues.CURRENT_LANGUAGE);

        Tariff tariff = null;

        try {
            TariffDbService dbService = new MySqlTariffDbService();
            tariff = dbService.getById(language, tariffId);

        } catch (DBException e) {
            LOG.warn("There is some problem with obtaining a Tariff from data base", e);
            throw new ServletException("There is some problem with obtaining a Tariff from data base", e);
        }

        return tariff;
    }
}
