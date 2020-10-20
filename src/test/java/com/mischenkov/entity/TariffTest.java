package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TariffTest {

    public Tariff tariff;

    @Before
    public void initTariff() {
        tariff = new Tariff();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, tariff.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        tariff.setId(exp);
        Assert.assertEquals(exp, tariff.getId());
    }

    @Test
    public void getPrice() {
        int exp = 0;
        Assert.assertEquals(exp, tariff.getPrice());
    }

    @Test
    public void setPrice() {
        int exp = 100;
        tariff.setPrice(exp);
        Assert.assertEquals(exp, tariff.getPrice());
    }

    @Test
    public void isActive() {
        Assert.assertFalse(tariff.isActive());
    }

    @Test
    public void setActive() {
        boolean exp = true;
        tariff.setActive(exp);
        Assert.assertTrue(tariff.isActive());
    }

    @Test
    public void setActive1() {
        boolean exp = true;
        tariff.setActive(exp);
        Assert.assertTrue(tariff.isActive());
    }

    @Test
    public void getTitle() {
        String exp = "internet";
        tariff.setTitle(exp);
        Assert.assertEquals(exp, tariff.getTitle());
    }

    @Test
    public void setTitle() {
        String exp = "internet";
        tariff.setTitle(exp);
        Assert.assertEquals(exp, tariff.getTitle());
    }

    @Test
    public void getDescription() {
        String exp = "internet";
        tariff.setDescription(exp);
        Assert.assertEquals(exp, tariff.getDescription());
    }

    @Test
    public void setDescription() {
        String exp = "internet";
        tariff.setDescription(exp);
        Assert.assertEquals(exp, tariff.getDescription());
    }

    @Test
    public void getShortDescription() {
        String exp = "internet";
        tariff.setShortDescription(exp);
        Assert.assertEquals(exp, tariff.getShortDescription());
    }

    @Test
    public void setShortDescription() {
        String exp = "internet";
        tariff.setShortDescription(exp);
        Assert.assertEquals(exp, tariff.getShortDescription());
    }

    @Test
    public void getStartDate() {
        String exp = "2020-01-02";
        tariff.setStartDate(exp);
        Assert.assertEquals(exp, tariff.getStartDate());
    }

    @Test
    public void setStartDate() {
        String exp = "2020-01-02";
        tariff.setStartDate(exp);
        Assert.assertEquals(exp, tariff.getStartDate());
    }

    @Test
    public void getEndDate() {
        String exp = "2020-01-02";
        tariff.setEndDate(exp);
        Assert.assertEquals(exp, tariff.getEndDate());
    }

    @Test
    public void setEndDate() {
        String exp = "2020-01-02";
        tariff.setEndDate(exp);
        Assert.assertEquals(exp, tariff.getEndDate());
    }

    @Test
    public void pricePerWeeks() {
        tariff.setPrice(100);
        Assert.assertEquals(7.0d, tariff.getPricePerWeeks(1), 0.5d);
    }

    @Test
    public void getPricePerDay() {
        tariff.setPrice(100);
        Assert.assertEquals(1.0d, tariff.getPricePerDay(), 0.5d);
    }

    @Test
    public void getPricePerWeeks() {
        tariff.setPrice(100);
        Assert.assertEquals(7.0d, tariff.getPricePerWeeks(1), 0.5d);
    }
}