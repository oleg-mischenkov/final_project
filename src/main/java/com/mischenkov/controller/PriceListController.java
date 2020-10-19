package com.mischenkov.controller;

import com.mischenkov.dtm.ServiceBox;
import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.entity.price.list.PriceList;
import com.mischenkov.entity.price.list.PriceListFactory;
import com.mischenkov.entity.price.list.PriceListNotImplementationException;
import com.mischenkov.model.dbservice.MySqlServiceDbService;
import com.mischenkov.model.dbservice.MySqlTariffDbService;
import com.mischenkov.model.dbservice.ServiceDbService;
import com.mischenkov.model.dbservice.TariffDbService;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.mischenkov.controller.CommonControllerValues.CURRENT_LANGUAGE;

/**
 *  The servlet generates and sends a list of tariffs for the user.
 */
@WebServlet("/price-list")
public class PriceListController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(PriceListController.class);

    private static final String REQ_PARAM_DOWNLOAD_FORMAT = "format";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String format = req.getParameter(REQ_PARAM_DOWNLOAD_FORMAT);

        // get language
        req.getRequestDispatcher("current-language").include(req, resp);
        Language currentLanguage = (Language) req.getAttribute( CURRENT_LANGUAGE );

        // get all services
        List<Service> serviceList = obtainAllServicesFromDb(currentLanguage);

        // get list of ServiceBoxes
        List<ServiceBox> serviceBoxList = obtainServiceBoxList(currentLanguage, serviceList);

        byte[] buffer = null;

        try {
            PriceList priceList = PriceListFactory.getPriceList( format );
            buffer = priceList.deriveData(serviceBoxList);

        } catch (PriceListNotImplementationException e) {
            e.printStackTrace();
        }

        String currentDate = getCurrentDate();
        String outFileName = String.format("\"attachment; filename=price-list_%s.zip;\"", currentDate);

        resp.setContentType("application/zip, application/octet-stream");
        resp.setHeader("Content-disposition", outFileName);

        OutputStream os = null;
        ZipOutputStream zipOutputStream = null;


        try {
            os = resp.getOutputStream();
            zipOutputStream = new ZipOutputStream(os);

            ZipEntry zipEntry = new ZipEntry("price.".concat(format));

            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write( buffer );
            zipOutputStream.closeEntry();

            zipOutputStream.finish();
        } finally {
            zipOutputStream.close();
        }

    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        return dateFormat.format( new Date() );
    }

    private List<ServiceBox> obtainServiceBoxList(Language currentLanguage, List<Service> serviceList) throws ServletException {
        List<ServiceBox> serviceBoxList = new ArrayList<>();

        try {
            TariffDbService dbService = new MySqlTariffDbService();

            for (Service service: serviceList) {
                int serviceId = service.getId();
                List<Tariff> tariffList = new ArrayList<>();

                tariffList.addAll(
                        dbService.getAll(currentLanguage, serviceId)
                );

                ServiceBox serviceBox = new ServiceBox(service, tariffList);
                serviceBoxList.add(serviceBox);
            }
        } catch (DBException e) {
            LOG.warn("Cant obtain some Tariff list from data base", e);
            throw new ServletException("Cant obtain some Tariff list from data base", e);
        }

        return serviceBoxList;
    }

    private List<Service> obtainAllServicesFromDb(Language language) throws ServletException {
        List<Service> serviceList = new ArrayList<>();

        try {
            ServiceDbService dbService = new MySqlServiceDbService();
            serviceList.addAll(
                    dbService.getAll(language)
            );
        } catch (DBException e) {
            LOG.warn("Cant obtain some Service list from data base.", e);
            throw new ServletException("Cant obtain some Service list from data base.", e);
        }

        return serviceList;
    }
}
