package com.mischenkov.entity.price.list;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PriceListFactoryTest {

    @Test(expected = PriceListNotImplementationException.class)
    public void getPriceList() throws PriceListNotImplementationException {
        PriceList txtPriceList = PriceListFactory.getPriceList("txt");
        Assert.assertNotNull(txtPriceList);

        PriceList xlsPriceList = PriceListFactory.getPriceList("xls");
        Assert.assertNotNull(xlsPriceList);

        PriceListFactory.getPriceList("txcct");
    }
}