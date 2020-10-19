package com.mischenkov.controller;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.listener.ContextVariable;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mischenkov.controller.CommonControllerValues.CURRENT_LANGUAGE;

/**
 *  The servlet handles requests from the home page of the site.
 */
public class MainController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    public static final String REQ_ATTR_SERVICES = "services";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOGGER.info( getServletContext().getInitParameter(ContextVariable.CONTEXT_INIT_DEFAULT_LANG) );

        req.getRequestDispatcher("current-language").include(req, resp);
        Language currentLanguage = (Language) req.getAttribute( CURRENT_LANGUAGE );

        List<Service> serviceList = obtainServicesFromDb(currentLanguage);

        req.setAttribute(REQ_ATTR_SERVICES, serviceList);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    private List<Service> obtainServicesFromDb(Language currentLanguage) throws ServletException {
        List<Service> serviceList = new ArrayList<>();

        try {
            ServiceDbService dbService = new MySqlServiceDbService();
            serviceList.addAll(
                    dbService.getAll(currentLanguage)
            );
        } catch (DBException e) {
            LOGGER.warn("Cant obtain some services from data base", e);
            throw new ServletException("Cant obtain some services from data base", e);
        }

        return serviceList;
    }

}
