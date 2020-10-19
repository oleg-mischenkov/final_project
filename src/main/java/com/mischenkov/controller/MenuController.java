package com.mischenkov.controller;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  The servlet participates in the formation of the site's main menu.
 */
public class MenuController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(MenuController.class);

    public static final String REQ_ATTR_MENU_SERVICES = "menuServices";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("service(req, resp) START");

        req.getRequestDispatcher("current-language").include(req, resp);
        Language language = (Language) req.getAttribute(CommonControllerValues.CURRENT_LANGUAGE);

        List<Service> serviceList = obtainCurrentServices(language);

        serviceList = serviceList.stream()
                .filter( service -> service.isActive() )
                .collect(Collectors.toList());

        req.setAttribute(REQ_ATTR_MENU_SERVICES, serviceList);

        req.getRequestDispatcher("/WEB-INF/view/pattern/left-menu.jsp").include(req, resp);
    }

    private List<Service> obtainCurrentServices(Language language) throws ServletException {
        List<Service> serviceList = null;

        try {
            ServiceDbService dbService = new MySqlServiceDbService();
            serviceList = dbService.getAll(language);

        } catch (DBException e) {
            LOG.warn("Some problem with data base");
            throw new ServletException(e);
        }

        return serviceList;
    }

}
