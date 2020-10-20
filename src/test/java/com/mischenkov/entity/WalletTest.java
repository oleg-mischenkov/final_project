package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WalletTest {

    public Wallet wallet;

    @Before
    public void initWallet() {
        wallet = new Wallet();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, wallet.getId());
    }

    @Test
    public void setId() {
        int exp = 15;
        wallet.setId(exp);
        Assert.assertEquals(exp, wallet.getId());
    }

    @Test
    public void getUserId() {
        int exp = 0;
        Assert.assertEquals(exp, wallet.getUserId());
    }

    @Test
    public void setUserId() {
        int exp = 11;
        wallet.setUserId(exp);
        Assert.assertEquals(exp, wallet.getUserId());
    }

    @Test
    public void getMoney() {
        int exp = 0;
        Assert.assertEquals(exp, wallet.getMoney());
    }

    @Test
    public void setMoney() {
        int exp = 100;
        wallet.setMoney(exp);
        Assert.assertEquals(exp, wallet.getMoney());
    }

    @Test
    public void getBlockedMoney() {
        int exp = 0;
        Assert.assertEquals(exp, wallet.getBlockedMoney());
    }

    @Test
    public void setBlockedMoney() {
        int exp = 100;
        wallet.setBlockedMoney(exp);
        Assert.assertEquals(exp, wallet.getBlockedMoney());
    }

    @Test
    public void getMoneyInDouble() {
        double exp = 5.0d;
        wallet.setMoney(500);
        Assert.assertEquals(exp, wallet.getMoneyInDouble(), 0.5d);
    }

    @Test
    public void getBlockedMoneyInDouble() {
        double exp = 0.0d;
        wallet.setMoney(5000);
        Assert.assertEquals(exp, wallet.getBlockedMoneyInDouble(), 0.5d);
    }
}