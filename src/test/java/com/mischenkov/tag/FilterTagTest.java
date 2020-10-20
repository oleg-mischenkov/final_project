package com.mischenkov.tag;

import com.mischenkov.entity.Service;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.jsp.JspException;

import static org.junit.Assert.*;

public class FilterTagTest {

    @Test
    public void setId() {
        FilterTag filterTag = new FilterTag();
        filterTag.setId("1");
        Assert.assertNotNull(filterTag);
    }

    @Test
    public void setService() {
        FilterTag filterTag = new FilterTag();
        filterTag.setService(new Service());
        Assert.assertNotNull(filterTag);
    }

    @Test
    public void setOptionNatural() {
        FilterTag filterTag = new FilterTag();
        filterTag.setOptionNatural("opt");
        Assert.assertNotNull(filterTag);
    }

    @Test
    public void setOptionPriceHigh() {
        FilterTag filterTag = new FilterTag();
        filterTag.setOptionPriceHigh("ph");
        Assert.assertNotNull(filterTag);
    }

    @Test
    public void setOptionPriceLow() {
        FilterTag filterTag = new FilterTag();
        filterTag.setOptionPriceLow("pl");
        Assert.assertNotNull(filterTag);
    }

    @Test
    public void setOptionTitleA() {
        FilterTag filterTag = new FilterTag();
        filterTag.setOptionTitleA("ta");
        Assert.assertNotNull(filterTag);
    }

    @Test
    public void setOptionTitleZ() {
        FilterTag filterTag = new FilterTag();
        filterTag.setOptionTitleZ("tz");
        Assert.assertNotNull(filterTag);
    }

}