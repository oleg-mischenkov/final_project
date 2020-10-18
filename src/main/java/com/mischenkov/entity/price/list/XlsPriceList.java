package com.mischenkov.entity.price.list;

import com.mischenkov.dtm.ServiceBox;
import com.mischenkov.entity.Tariff;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class XlsPriceList implements PriceList {

    private static final Logger LOG = Logger.getLogger(XlsPriceList.class);

    @Override
    public byte[] deriveData(List<ServiceBox> serviceBoxList) {
        Objects.requireNonNull(serviceBoxList, "deriveData(List<ServiceBox> serviceBoxList), \"serviceBoxList\" is null.");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Price");

        int rowNumber = 0;

        for (ServiceBox serviceBox: serviceBoxList) {
            String serviceName = serviceBox.getService().getTitle();

            Row row = sheet.createRow( rowNumber );
            Cell cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(serviceName);

            for (Tariff tariff: serviceBox.getTariffs()) {
                String title = tariff.getTitle();
                String shortDescription = tariff.getShortDescription();
                float price = Math.round( tariff.getPrice() / 100 );

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(title);

                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(price);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(shortDescription);

                rowNumber++;
                row = sheet.createRow( rowNumber );
            }

        }

        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            LOG.warn("Cant write data to the output stream");
        }

        return byteArrayOutputStream.toByteArray();
    }
}
