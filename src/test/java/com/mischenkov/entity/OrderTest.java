package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OrderTest {

    public Order order;

    @Before
    public void initOrder() {
        order = new Order();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, order.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        order.setId(exp);
        Assert.assertEquals(exp, order.getId());
    }

    @Test
    public void getItemList() {
        Assert.assertNotNull(order.getItemList());
    }

    @Test
    public void setItemList() {
        order.setItemList(new ArrayList<Tariff>());
        Assert.assertNotNull(order.getItemList());
    }

    @Test
    public void isHasItem() {
        Assert.assertFalse( order.isHasItem(2) );
    }

    @Test
    public void getTotal() {
        int exp = 0;
        Assert.assertEquals(exp, order.getTotal());
    }

    @Test
    public void setTotal() {
        int exp = 230;
        order.setTotal(exp);
        Assert.assertEquals(exp, order.getTotal());
    }

    @Test
    public void getTotalInDouble() {
        double exp = .0;
        Assert.assertEquals(exp, order.getTotalInDouble(), .0);
    }
}