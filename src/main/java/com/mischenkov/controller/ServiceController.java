package com.mischenkov.controller;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Paginator;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.SqlOrderBy;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.MySqlTariffDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.dbservice.TariffDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.validation.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mischenkov.controller.CommonControllerValues.CURRENT_LANGUAGE;
import static com.mischenkov.model.validation.Validator.*;

/**
 *  Servlet renders pages with services.
 */
@WebServlet("/service")
public class ServiceController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ServiceController.class);

    private static final int DEFAULT_ROW_COUNT = CommonControllerValues.DEFAULT_ROW_COUNT;
    private static final int DEFAULT_START_POSITION = CommonControllerValues.DEFAULT_START_POSITION;
    private static final String DEFAULT_SORT_FIELD = "tariff_id";
    private static final SqlOrderBy DEFAULT_SORT_DIRECTION = SqlOrderBy.ASC;
    // request parameters
    public static final String REQ_PARAM_SERVICE = "service";
    private static final String REQ_PARAM_COUNT = CommonControllerValues.REQ_PARAM_COUNT;
    private static final String REQ_PARAM_POSITION = CommonControllerValues.REQ_PARAM_POSITION;
    private static final String REQ_PARAM_SORT_FIELD = "sort-field";
    private static final String REQ_PARAM_SORT_DIRECTION = "sort-direction";
    // request attributes
    public static final String REQ_ATTR_CURRENT_SERVICE = "currentService";
    public static final String REQ_ATTR_CURRENT_TARIFFS = "currentTariffs";
    public static final String REQ_ATR_PAGINATION_URLS = CommonControllerValues.REQ_ATR_PAGINATION_URLS;
    public static final String REQ_ATR_START_POSITION = "position";
    public static final String REQ_ATR_COUNT = "currentCount";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("doGet(req, resp)");
        // current service
        String preService = req.getParameter(REQ_PARAM_SERVICE);
        numberValidation(preService, req);
        int serviceId = Integer.parseInt(preService);

        // obtain pagination field
        String preCount = req.getParameter(REQ_PARAM_COUNT);
        String prePosition = req.getParameter(REQ_PARAM_POSITION);
        int count;
        int position;

        if ( (preCount != null) && (prePosition != null) ) {
            numberValidation(preCount, req);
            numberValidation(prePosition, req);

            count = Integer.parseInt(preCount);
            position = Integer.parseInt(prePosition);
        } else {
            count = DEFAULT_ROW_COUNT;
            position = DEFAULT_START_POSITION;
        }
        LOG.debug("count = " + count);
        LOG.debug("position = " + position);

        // obtain sort field
        String preSortField = getNotNumberValueFromRequest( REQ_PARAM_SORT_FIELD, req );
        LOG.debug("preSortField = " + preSortField);
        String preSortDirection = getNotNumberValueFromRequest( REQ_PARAM_SORT_DIRECTION, req );
        LOG.debug("preSortDirection = " + preSortDirection);
        String sortField = null;
        SqlOrderBy sortDirection = null;

        if ( preSortField.length() > 0 ) {
            sortField = preSortField;
        } else {
            sortField = DEFAULT_SORT_FIELD;
        }

        if ( preSortDirection.length() > 0 ) {
            try {
                sortDirection = SqlOrderBy.valueOf(preSortDirection);
            } catch (IllegalArgumentException e) {
                LOG.warn("Invalid sort direction value [" + preSortDirection + ']', e);
                req.getRequestDispatcher("error").forward(req, resp);
            }
        } else {
            sortDirection = DEFAULT_SORT_DIRECTION;
        }

        LOG.debug("sortField = " + sortField);
        LOG.debug("sortDirection = " + sortDirection);

        // obtain language
        req.getRequestDispatcher("current-language").include(req, resp);
        Language currentLanguage = (Language) req.getAttribute( CURRENT_LANGUAGE );

        // obtain data from data base
        Service service = getServiceFromDb(serviceId, currentLanguage);

        List<Tariff> tariffList = getTariffsFromDb(currentLanguage, service.getId(), sortField, sortDirection, position, count);

        LOG.debug("tariffList = " + tariffList);
        tariffList = tariffList.stream()
                .filter( tariff -> tariff.isActive() )
                .collect(Collectors.toList());

        int totalTariff = getCountTariffFromDb(service);

        req.setAttribute(REQ_ATTR_CURRENT_SERVICE, service);
        req.setAttribute(REQ_ATTR_CURRENT_TARIFFS, tariffList);
        req.setAttribute(REQ_ATR_COUNT, count);
        req.setAttribute(REQ_ATR_START_POSITION, position);
        req.setAttribute(REQ_ATR_PAGINATION_URLS, Paginator.getPages(count, totalTariff));

        req.getRequestDispatcher("WEB-INF/view/service.jsp").forward(req, resp);
    }

    private int getCountTariffFromDb(Service service) throws ServletException {
        int result = 0;

        try {
            TariffDbService dbService = new MySqlTariffDbService();
            result = dbService.count(service);
        } catch (DBException e) {
            LOG.warn("Cant obtain tariffs count.", e);
            throw new ServletException("Cant obtain tariffs count from data base.", e);
        }

        return result;
    }

    private List<Tariff> getTariffsFromDb(Language language, int serviceId, String sortField, SqlOrderBy orderBy, int position, int count) throws ServletException {
        List<Tariff> tariffList = new ArrayList<>();

        try {
            TariffDbService dbService = new MySqlTariffDbService();
            tariffList.addAll(
                    dbService.getAll(language, serviceId, sortField, orderBy, position, count)
            );
        } catch (DBException e) {
            LOG.warn("Cant obtain Service.", e);
            throw new ServletException("Cant obtain Tariffs list from data base.", e);
        }

        return tariffList;
    }

    private String getNotNumberValueFromRequest(String parameter, HttpServletRequest req) {
        String result = "";
        String preParamValue = req.getParameter(parameter);
        LOG.debug(" IN preParamValue = " + preParamValue);
        Validator validator = new Validator();
        boolean validationFlag = validator
                .append( !isNull(preParamValue) )
                .append( !match("^[\\d]{1,}$", preParamValue) )
                .validate();

        LOG.debug(" validationFlag = " + validationFlag);
        if ( validationFlag ) {
            result = result.concat(preParamValue);
        }

        LOG.debug(" out preParamValue = " + result);
        return result;
    }

    private Service getServiceFromDb(int serviceId, Language currentLanguage) throws ServletException {
        Service service = null;

        try {
            Service tempService = new Service();
            tempService.setId( serviceId );

            ServiceDbService dbService = new MySqlServiceDbService();
            service = dbService.getById( tempService, currentLanguage );

        } catch (DBException e) {
            LOG.warn("Cant obtain Service.", e);
            throw new ServletException("Cant obtain Service from data base.", e);
        }

        return service;
    }

    private void numberValidation(String variable, HttpServletRequest req) {
        Validator validator = new Validator();
        
        validator
                .append( !isNull(variable) )
                .append( variable, isNumber )
                .ifInvalidate( ()->
                    req.getRequestDispatcher("error")
                );
    }

}
