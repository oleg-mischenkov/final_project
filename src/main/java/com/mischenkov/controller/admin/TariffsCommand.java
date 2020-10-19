package com.mischenkov.controller.admin;

import com.mischenkov.cookie.CookieCommon;
import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.listener.ContextVariable;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.MySqlTariffDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.dbservice.TariffDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.validation.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mischenkov.model.validation.Validator.isNull;
import static com.mischenkov.model.validation.Validator.isNumber;

/**
 *  Handler pages tariffs.
 */
public class TariffsCommand extends AdminAbstractCommand {

    private static final Logger LOG = Logger.getLogger(TariffsCommand.class);

    // variables of the servlet context
    private static final String CONTEXT_INIT_DEFAULT_LANG = ContextVariable.CONTEXT_INIT_DEFAULT_LANG;
    private static final String CONTEXT_CURRENT_LANGS = ContextVariable.CONTEXT_CURRENT_LANGS;
    // cookie value
    private static final String COOKIE_LANGUAGE = CookieCommon.COOKIE_LANGUAGE;
    // request attributes
    public static final String REQ_ATTR_SERVICES = "allServices";
    public static final String REQ_ATTR_SELECTED_SERVICE = "selectedServiceId";
    public static final String REQ_ATTR_TARIFFS = "tariffList";
    // request parameters
    private static final String REQ_PARAM_SERVICE_ID = "service";
    private static final String REQ_PARAM_TARIFF_ID = "tariff";
    private static final String REQ_PARAM_SERVICE_ACTIVE = "active";
    private static final String REQ_PARAM_SELECTED_SERVICE = "serv-inf";

    @Override
    public void getMake() throws IOException, ServletException {
        String preService = req.getParameter(REQ_PARAM_SERVICE_ID);
        String preActive = req.getParameter(REQ_PARAM_SERVICE_ACTIVE);

        int service = getValidNumberValueIfNotExists(preService);
        int active = getValidNumberValueIfNotExists(preActive);

        changeServiceActive(service, active);

        String preTariff = req.getParameter(REQ_PARAM_TARIFF_ID);
        int tariff = getValidNumberValueIfNotExists(preTariff);

        changeTariffActive(tariff, active);

        List<Service> serviceList = new ArrayList<>();
        Language currentLang = getCurrentLanguage(req);

        String preSelectedService = req.getParameter(REQ_PARAM_SELECTED_SERVICE);
        int selectedService = getValidNumberValueIfNotExists(preSelectedService);

        addTariffs(selectedService, currentLang);

        try {
            ServiceDbService dbService = new MySqlServiceDbService();
            serviceList.addAll(
                    dbService.getAll(currentLang)
            );
        } catch (DBException e) {
            LOG.warn("It cant obtain some List of Service", e);
        }

        req.setAttribute(REQ_ATTR_SERVICES, serviceList);
        forward("tariffs");


    }

    private void changeTariffActive(int tariff, int active) {
        if (tariff != -1 && active != -1) {
            try {
                TariffDbService dbService = new MySqlTariffDbService();
                dbService.updateTariffActive(tariff, active);
            } catch (DBException e) {
                LOG.warn("Can not change some Tariff active status, which has id [" + tariff + ']');
            }
        }
    }

    private void addTariffs(int selectedService, Language currentLang) {
        if ( selectedService != -1 ) {
            try {
                TariffDbService dbService = new MySqlTariffDbService();
                List<Tariff> tariffList = dbService.getAll(currentLang, selectedService);

                req.setAttribute(REQ_ATTR_TARIFFS, tariffList);
                req.setAttribute(REQ_ATTR_SELECTED_SERVICE, selectedService);
            } catch (DBException e) {
                LOG.warn("Can not change some Tariffs for Service id [" + selectedService + ']');
            }
        }
    }

    private void changeServiceActive(int service, int active) {
        if (service != -1 && active != -1) {
            try {
                ServiceDbService dbService = new MySqlServiceDbService();
                dbService.updateServiceActive(service, active);
            } catch (DBException e) {
                LOG.warn("Can not change some Service active status, which has id [" + service + ']');
            }
        }
    }

    private int getValidNumberValueIfNotExists(String str) {
        int result = -1;

        Validator validator = new Validator();
        boolean validFlag = validator
                .append( !isNull(str) )
                .append( str, isNumber ).validate();

        if (validFlag) {
            result = Integer.valueOf(str);
        }

        return result;
    }

    private Language getCurrentLanguage(HttpServletRequest req) throws ServletException {
        Language language = null;
        String userLanguage = "";
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            userLanguage = Arrays.stream( cookies )
                    .filter( elem -> COOKIE_LANGUAGE.equals( elem.getName() ) )
                    .map( Cookie::getValue )
                    .findFirst()
                    .orElse( req.getServletContext().getInitParameter(CONTEXT_INIT_DEFAULT_LANG) );
        }

        List<Language> languageList = (List<Language>) req.getServletContext().getAttribute(CONTEXT_CURRENT_LANGS);

        if (languageList != null) {
            language = obtainLanguage(languageList, userLanguage);
        }

        return language;
    }

    private Language obtainLanguage(List<Language> languageList, String userLanguage) throws ServletException {
        Language result = null;

        for (Language element: languageList) {
            String elemLanguage = element.getIso639_1();

            if ( userLanguage.equals(elemLanguage) ) {
                result = element;
                break;
            }
        }

        if (result == null) {
            LOG.warn("Language obtainLanguage(List<Language> languageList, String userLanguage) language is not exist");
            throw new ServletException("language is not exist");
        }
        return result;
    }
}
