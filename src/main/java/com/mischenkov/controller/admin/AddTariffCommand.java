package com.mischenkov.controller.admin;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Tariff;
import com.mischenkov.listener.ContextVariable;
import com.mischenkov.model.dbservice.MySqlTariffDbService;
import com.mischenkov.model.dbservice.TariffDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.validation.ValidPattern;
import com.mischenkov.model.validation.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.mischenkov.controller.admin.AddServiceCommand.REQ_ATR_END_DATE;
import static com.mischenkov.controller.admin.AddServiceCommand.REQ_ATR_START_DATE;
import static com.mischenkov.model.validation.Validator.isNull;
import static com.mischenkov.model.validation.Validator.isNumber;

public class AddTariffCommand extends SpecificValidationCommand {

    private static final Logger LOG = Logger.getLogger(AddTariffCommand.class);

    // request parameters
    private static final String REQ_PARAM_ACTIVE = AddServiceCommand.REQ_PARAM_ACTIVE;
    private static final String REQ_PARAM_START_DATE = AddServiceCommand.REQ_PARAM_START_DATE;
    private static final String REQ_PARAM_END_DATE = AddServiceCommand.REQ_PARAM_END_DATE;
    public static final String REQ_PARAM_SERVICE = "service";
    // request attribute
    public static final String REQ_ATTR_PATTERN_ERROR_MSG = UsersCommand.REQ_ATR_PATTERN_ERROR_MSG;
    public static final String REQ_ATTR_ACTIVE = "active";
    public static final String REQ_ATTR_PRICE = "price";
    // context variable
    private static final String CONTEXT_CURRENT_LANGS = ContextVariable.CONTEXT_CURRENT_LANGS;
    // search pattern
    private static final String SEARCH_PATTERN_TITLE = "title(%s)";
    private static final String SEARCH_PATTERN_SHORT_DESCRIPTION = "short-description(%s)";
    private static final String SEARCH_PATTERN_DESCRIPTION = "description(%s)";

    @Override
    public void postMake() throws IOException, ServletException {
        String preServiceId = req.getParameter(REQ_PARAM_SERVICE);

        LOG.debug("preServiceId = " + preServiceId);

        Validator validator = new Validator();
        validator
                .append( !isNull(preServiceId) )
                .append( preServiceId, isNumber )
                .ifInvalidate( invalidateCommand );

        int serviceId = Integer.parseInt(preServiceId);

        String startDate = req.getParameter(REQ_PARAM_START_DATE);
        String endDate = req.getParameter(REQ_PARAM_END_DATE);

        int active = 0;
        if ( req.getParameter(REQ_PARAM_ACTIVE) != null ) {
            active = req.getParameter(REQ_PARAM_ACTIVE).equals("on") ? 1 : 0;
        }

        String price = req.getParameter(REQ_ATTR_PRICE).replace(",", ".");

        reqParamValidate(startDate, ValidPattern.DATE);
        reqParamValidate(startDate, ValidPattern.DATE);
        reqParamValidate(price, ValidPattern.PRICE_DECIMAL);

        req.setAttribute(REQ_ATR_END_DATE, endDate);
        req.setAttribute(REQ_ATR_START_DATE, startDate);
        req.setAttribute(REQ_ATTR_ACTIVE, active);
        req.setAttribute(REQ_ATTR_PRICE, price);

        Map<Integer, Tariff> localizedTariffMap = obtainTariff();

        try {
            TariffDbService dbService = new MySqlTariffDbService();
            dbService.saveTariff(localizedTariffMap, serviceId);
        } catch (DBException e) {
            LOG.warn("Cant insert some service to the data base");
            forward("error");
        }

        resp.sendRedirect("admin?command=tariffs");
    }

    private Map<Integer, Tariff> obtainTariff() {
        Map<Integer, Tariff> localizedTariffMap = new TreeMap<>();

        List<Language> languageList = (List<Language>) req.getServletContext().getAttribute(CONTEXT_CURRENT_LANGS);

        addTariffToMap(localizedTariffMap, languageList);

        return localizedTariffMap;
    }

    private void addTariffToMap(Map<Integer, Tariff> localizedTariffMap, List<Language> languageList) {
        for (Language language: languageList) {
            Tariff tariff = new Tariff();
            int languageId = language.getId();

            tariff.setStartDate(
                    (String) req.getAttribute(REQ_ATR_START_DATE)
            );

            tariff.setEndDate(
                    (String) req.getAttribute(REQ_ATR_END_DATE)
            );

            String prePrice = (String) req.getAttribute(REQ_ATTR_PRICE);
            float floatPrice = Float.parseFloat(prePrice) * 100;
            int price = Math.round(floatPrice);
            tariff.setPrice(price);

            String title = obtainRequestParam(SEARCH_PATTERN_TITLE, languageId);
            tariff.setTitle(title);

            tariff.setActive(
                    (Integer) req.getAttribute(REQ_ATTR_ACTIVE)
            );

            String description = obtainRequestParam(SEARCH_PATTERN_DESCRIPTION, languageId);
            tariff.setDescription(description);

            String shortDescription = obtainRequestParam(SEARCH_PATTERN_SHORT_DESCRIPTION, languageId);
            tariff.setShortDescription(shortDescription);

            localizedTariffMap.put(languageId, tariff);
        }
    }

    @Override
    public void getMake() throws IOException, ServletException {
        LOG.debug("getMake");

        String service = req.getParameter(REQ_PARAM_SERVICE);

        Validator validator = new Validator();
        validator
                .append( !isNull(service) )
                .append( service, isNumber )
                .ifInvalidate( invalidateCommand );

        req.setAttribute(REQ_PARAM_SERVICE, service);

        forward("add-tariff");
    }
}
