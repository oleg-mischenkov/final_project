package com.mischenkov.entity.price.list;

import org.apache.log4j.Logger;

import java.util.Objects;

public abstract class PriceListFactory {

    private static final Logger LOG = Logger.getLogger(PriceListFactory.class);

    private static final String PRICE_LIST_CLASS_PATH_PATTERN = "com.mischenkov.entity.price.list.%sPriceList";

    public static PriceList getPriceList(String priceListName) throws PriceListNotImplementationException {
        Objects.requireNonNull(priceListName, "getPriceList(String priceListName), \"priceListName\" is null.");

        priceListName = firstLetterUp(
                priceListName.toLowerCase()
        );

        PriceList result = null;

        String classPath = String.format(PRICE_LIST_CLASS_PATH_PATTERN, priceListName);

        try {
            Class priceListClass = Class.forName( classPath );
            result = (PriceList) priceListClass.asSubclass( PriceList.class ).newInstance();

        } catch (Exception e) {
            LOG.warn("The system does not have the [" + classPath + "] class.", e);
            throw new PriceListNotImplementationException("The system does not have the [" + classPath + "] class.", e);
        }

        return result;
    }

    private static String firstLetterUp(String command) {

        if (command != null && command.length() > 1) {
            char[] letters = command.toCharArray();
            letters[0] = Character.toUpperCase( letters[0] );

            command = new String(letters);
        }

        return command;
    }
}
