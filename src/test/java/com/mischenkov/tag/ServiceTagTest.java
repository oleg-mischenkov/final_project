package com.mischenkov.tag;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceTagTest {

    @Test
    public void setServices() {
        ServiceTag serviceTag = new ServiceTag();
        Assert.assertNotNull(serviceTag);
    }
}