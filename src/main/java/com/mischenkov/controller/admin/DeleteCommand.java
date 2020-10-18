package com.mischenkov.controller.admin;

import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.MySqlTariffDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.dbservice.TariffDbService;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public class DeleteCommand extends SpecificValidationCommand {

    private static final Logger LOG = Logger.getLogger(DeleteCommand.class);

    private static final String REQ_PARAM_TARIFF = "tariff";
    private static final String REQ_PARAM_SERVICE = "service";

    @Override
    public void make() throws IOException, ServletException {

        String preTariffId = req.getParameter(REQ_PARAM_TARIFF);
        String preServiceId = req.getParameter(REQ_PARAM_SERVICE);

        int tariffId = validateNumber(preTariffId);
        LOG.info("delete tariff id [" + tariffId + ']');

        int serviceId = validateNumber(preServiceId);
        LOG.info("delete service id [" + serviceId + ']');

        if (tariffId != -1) {
            Tariff tariff = new Tariff();
            tariff.setId(tariffId);

            try {
                TariffDbService dbService = new MySqlTariffDbService();
                dbService.delete(tariff);
                LOG.debug("Tariff delete");
            } catch (DBException e) {
                LOG.warn("Cent delete some Service");
                req.setAttribute(REQ_ATR_PATTERN_ERROR_MSG, "delete operation error ");
            }

        } else {
            Service service = new Service();
            service.setId(serviceId);

            try {
                ServiceDbService dbService = new MySqlServiceDbService();
                dbService.delete(service);
                LOG.debug("Service delete");
            } catch (DBException e) {
                LOG.warn("Cent delete some Service");
                req.setAttribute(REQ_ATR_PATTERN_ERROR_MSG, "delete operation error ");
            }
        }

        resp.sendRedirect("admin?command=tariffs");
    }

}
