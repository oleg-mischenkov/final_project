package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceTest {

    public Service service;

    @Before
    public void initService() {
        service = new Service();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, service.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        service.setId(exp);
        Assert.assertEquals(exp, service.getId());
    }

    @Test
    public void isActive() {
        Assert.assertFalse(service.isActive());
    }

    @Test
    public void setActive() {
        boolean exp = true;
        service.setActive(exp);
        Assert.assertTrue(service.isActive());
    }

    @Test
    public void setActive1() {
        boolean exp = false;
        service.setActive(exp);
        Assert.assertFalse(service.isActive());
    }

    @Test
    public void getTitle() {
        String exp = "ip";
        service.setTitle(exp);
        Assert.assertEquals(exp, service.getTitle());
    }

    @Test
    public void setTitle() {
        String exp = "ierp";
        service.setTitle(exp);
        Assert.assertEquals(exp, service.getTitle());
    }

    @Test
    public void getDescription() {
        String exp = "desc";
        service.setDescription(exp);
        Assert.assertEquals(exp, service.getDescription());
    }

    @Test
    public void setDescription() {
        String exp = "desc";
        service.setDescription(exp);
        Assert.assertEquals(exp, service.getDescription());
    }

    @Test
    public void getShortDescription() {
        String exp = "desc";
        service.setShortDescription(exp);
        Assert.assertEquals(exp, service.getShortDescription());
    }

    @Test
    public void setShortDescription() {
        String exp = "desc";
        service.setShortDescription(exp);
        Assert.assertEquals(exp, service.getShortDescription());
    }

    @Test
    public void getStartDate() {
        String exp = "2019-09-09";
        service.setStartDate(exp);
        Assert.assertEquals(exp, service.getStartDate());
    }

    @Test
    public void setStartDate() {
        String exp = "2019-09-09";
        service.setStartDate(exp);
        Assert.assertEquals(exp, service.getStartDate());
    }

    @Test
    public void getEndDate() {
        String exp = "2019-09-09";
        service.setEndDate(exp);
        Assert.assertEquals(exp, service.getEndDate());
    }

    @Test
    public void setEndDate() {
        String exp = "2019-09-09";
        service.setEndDate(exp);
        Assert.assertEquals(exp, service.getEndDate());
    }
}