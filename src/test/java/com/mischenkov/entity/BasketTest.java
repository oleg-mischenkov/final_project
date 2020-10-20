package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasketTest {

    public Basket basket;

    @Before
    public void initBasket() {
        basket = new Basket();
    }

    @Test
    public void size() {
        int exp = 0;
        Assert.assertEquals(exp, basket.size());
    }

    @Test
    public void add() {
        int exp = 2;
        basket.add(new Tariff());
        basket.add(new Tariff());
        Assert.assertEquals(exp, basket.size());

    }

    @Test
    public void getAll() {
        Assert.assertNotNull(basket.getAll());
    }

    @Test
    public void totalMoney() {
        int exp = 0;
        Assert.assertEquals( exp, basket.totalMoney() );
    }

    @Test
    public void totalMoneyPerWeek() {
        int exp = 0;
        Assert.assertEquals( exp, basket.totalMoneyPerWeek(1) );
    }

    @Test
    public void totalMoneyPerWeekInDouble() {
        double exp = .0;
        Assert.assertEquals( exp, basket.totalMoneyPerWeekInDouble(1), .5 );
    }

    @Test
    public void totalMoneyInDouble() {
        double exp = .0;
        Assert.assertEquals( exp, basket.totalMoneyInDouble(), .5 );
    }

    @Test
    public void isHasTariff() {
        Assert.assertFalse( basket.isHasTariff(5) );
    }

    @Test
    public void deleteId() {
        int exp = 0;
        basket.add(new Tariff());
        basket.deleteId(0);
        Assert.assertEquals(exp, basket.size());
    }
}