package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageTest {

    public Language language;

    @Before
    public void initLanguage() {
        language = new Language();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, language.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        language.setId(exp);
        Assert.assertEquals(exp, language.getId());
    }

    @Test
    public void getTitle() {
        String exp = "ru";
        language.setTitle(exp);
        Assert.assertEquals(exp, language.getTitle());
    }

    @Test
    public void setTitle() {
        String exp = "ru";
        language.setTitle(exp);
        Assert.assertEquals(exp, language.getTitle());
    }

    @Test
    public void isActive() {
        Assert.assertFalse(language.isActive());
    }

    @Test
    public void setActive() {
        boolean exp = true;
        language.setActive(exp);
        Assert.assertTrue(language.isActive());
    }

    @Test
    public void setActive1() {
        boolean exp = false;
        language.setActive(exp);
        Assert.assertFalse(language.isActive());
    }

    @Test
    public void getIso639_1() {
        String exp = "ru";
        language.setIso639_1(exp);
        Assert.assertEquals(exp, language.getIso639_1());
    }

    @Test
    public void setIso639_1() {
        String exp = "ru";
        language.setIso639_1(exp);
        Assert.assertEquals(exp, language.getIso639_1());
    }

    @Test
    public void getIso639_2() {
        String exp = "ru";
        language.setIso639_2(exp);
        Assert.assertEquals(exp, language.getIso639_2());
    }

    @Test
    public void setIso639_2() {
        String exp = "ru";
        language.setIso639_2(exp);
        Assert.assertEquals(exp, language.getIso639_2());
    }

    @Test
    public void getIso639_3() {
        String exp = "ru";
        language.setIso639_3(exp);
        Assert.assertEquals(exp, language.getIso639_3());
    }

    @Test
    public void setIso639_3() {
        String exp = "ru";
        language.setIso639_3(exp);
        Assert.assertEquals(exp, language.getIso639_3());
    }

    @Test
    public void getCode() {
        int exp = 111;
        language.setCode(exp);
        Assert.assertEquals(exp, language.getCode());
    }

    @Test
    public void setCode() {
        int exp = 111;
        language.setCode(exp);
        Assert.assertEquals(exp, language.getCode());
    }
}