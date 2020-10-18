package com.mischenkov.entity.price.list;

import com.mischenkov.dtm.ServiceBox;
import com.mischenkov.entity.Tariff;

import java.util.List;
import java.util.Objects;

public class TxtPriceList implements PriceList {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public byte[] deriveData(List<ServiceBox> serviceBoxList) {
        Objects.requireNonNull(serviceBoxList, "deriveData(List<ServiceBox> serviceBoxList), \"serviceBoxList\" is null.");

        StringBuilder stringBuilder = new StringBuilder();

        for (ServiceBox serviceBox: serviceBoxList) {

            String serviceName = serviceBox.getService().getTitle();
            stringBuilder
                    .append(serviceName)
                    .append(LINE_SEPARATOR);

            List<Tariff> tariffList = serviceBox.getTariffs();

            for (Tariff tariff: tariffList) {
                String title = tariff.getTitle();
                String shortDescription = tariff.getShortDescription();
                float price = Math.round( tariff.getPrice() / 100 );

                stringBuilder
                        .append('\t')
                        .append("* ")
                        .append(title).append(';').append('\t')
                        .append(price).append(';').append('\t')
                        .append(shortDescription).append(';')
                        .append(LINE_SEPARATOR);
            }

        }

        return stringBuilder.toString().getBytes();
    }
}
