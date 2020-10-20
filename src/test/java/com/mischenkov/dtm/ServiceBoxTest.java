package com.mischenkov.dtm;

import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceBoxTest {

    public ServiceBox box;

    @Before
    public void initBox() {
        box = new ServiceBox(new Service(), new ArrayList<Tariff>());
    }

    @Test
    public void getService() {
        Service service = box.getService();
        Assert.assertNotNull(service);
    }

    @Test
    public void getTariffs() {
        List<Tariff> tariffList = box.getTariffs();
        Assert.assertNotNull(tariffList);
    }

    @Test
    public void equals() {
        Assert.assertFalse( box.equals(null) );
    }

    @Test
    public void hashCodeTest() {
        int hash = box.hashCode();
        Assert.assertNotNull(new Integer(hash));
    }

}