package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    public Card card;

    @Before
    public void initCard() {
        card = new Card();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, card.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        card.setId(exp);
        Assert.assertEquals(exp, card.getId());
    }

    @Test
    public void getMoney() {
        int exp = 0;
        Assert.assertEquals(exp, card.getMoney());
    }

    @Test
    public void setMoney() {
        int exp = 10;
        card.setMoney(exp);
        Assert.assertEquals(exp, card.getMoney());
    }

    @Test
    public void getCode() {
        int exp = 0;
        Assert.assertEquals(exp, card.getCode());
    }

    @Test
    public void setCode() {
        int exp = 10;
        card.setCode(exp);
        Assert.assertEquals(exp, card.getCode());
    }
}