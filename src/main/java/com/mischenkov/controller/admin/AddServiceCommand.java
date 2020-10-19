package com.mischenkov.controller.admin;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.listener.ContextVariable;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.validation.ValidPattern;
import com.mischenkov.model.validation.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.mischenkov.model.validation.Validator.isNull;
import static com.mischenkov.model.validation.Validator.match;

/**
 *  The handler adds and validates a new service.
 */
public class AddServiceCommand extends SpecificValidationCommand {

    private static final Logger LOG = Logger.getLogger(AddServiceCommand.class);

    // request parameters
    public static final String REQ_PARAM_ACTIVE = "active";
    public static final String REQ_PARAM_START_DATE = "start-date";
    public static final String REQ_PARAM_END_DATE = "end-date";
    // request attribute
    public static final String REQ_ATR_PATTERN_ERROR_MSG = UsersCommand.REQ_ATR_PATTERN_ERROR_MSG;
    public static final String REQ_ATR_START_DATE = "startDate";
    public static final String REQ_ATR_END_DATE = "endDate";
    public static final String REQ_ATR_ACTIVE = "active";
    // context variable
    private static final String CONTEXT_CURRENT_LANGS = ContextVariable.CONTEXT_CURRENT_LANGS;
    // search pattern
    private static final String SEARCH_PATTERN_TITLE = "title(%s)";
    private static final String SEARCH_PATTERN_SHORT_DESCRIPTION = "short-description(%s)";
    private static final String SEARCH_PATTERN_DESCRIPTION = "description(%s)";

    @Override
    public void postMake() throws IOException, ServletException {

        String startDate = req.getParameter(REQ_PARAM_START_DATE);
        reqParamValidate(startDate, ValidPattern.DATE);
        req.setAttribute(REQ_ATR_START_DATE, startDate);

        String endDate = req.getParameter(REQ_PARAM_END_DATE);
        reqParamValidate(startDate, ValidPattern.DATE);
        req.setAttribute(REQ_ATR_END_DATE, endDate);

        int active = 0;
        if ( req.getParameter(REQ_PARAM_ACTIVE) != null ) {
            active = req.getParameter(REQ_PARAM_ACTIVE).equals("on") ? 1 : 0;
        }
        req.setAttribute(REQ_ATR_ACTIVE, active);
        LOG.debug("active = " + active);

        Map<Integer, Service> localizedServiceMap = obtainService();

        try {

            ServiceDbService dbService = new MySqlServiceDbService();
            dbService.saveService(localizedServiceMap);

        } catch (DBException e) {
            LOG.warn("Cant insert some service to the data base");
            forward("error");
        }

        resp.sendRedirect("admin?command=tariffs");
    }

    private Map<Integer, Service> obtainService() {
        Map<Integer, Service> localizedServiceMap = new TreeMap<>();

        List<Language> languageList = (List<Language>) req.getServletContext().getAttribute(CONTEXT_CURRENT_LANGS);

        addServiceToMap(localizedServiceMap, languageList);


        return localizedServiceMap;
    }

    private void addServiceToMap(Map<Integer, Service> localizedServiceMap, List<Language> languageList) {
        for (Language language: languageList) {
            Service service = new Service();
            int languageId = language.getId();

            service.setStartDate(
                    (String) req.getAttribute(REQ_ATR_START_DATE)
            );

            service.setEndDate(
                    (String) req.getAttribute(REQ_ATR_END_DATE)
            );

            service.setActive(
                    (Integer) req.getAttribute(REQ_ATR_ACTIVE)
            );

            String title = obtainRequestParam(SEARCH_PATTERN_TITLE, languageId);
            service.setTitle(title);

            String shortDescription = obtainRequestParam(SEARCH_PATTERN_SHORT_DESCRIPTION, languageId);
            service.setShortDescription(shortDescription);

            String description = obtainRequestParam(SEARCH_PATTERN_DESCRIPTION, languageId);
            service.setDescription(description);

            localizedServiceMap.put(languageId, service);
        }
    }


    @Override
    public void getMake() throws IOException, ServletException {
        forward("add-service");
    }
}
