package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleTest {

    private Role role;

    @Before
    public void initRole() {
        role = new Role();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, role.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        role.setId(exp);
        Assert.assertEquals(exp, role.getId());
    }

    @Test
    public void getName() {
        String exp = "name";
        role.setName(exp);
        Assert.assertEquals(exp, role.getName());
    }

    @Test
    public void setName() {
        String exp = "name";
        role.setName(exp);
        Assert.assertEquals(exp, role.getName());
    }

    @Test
    public void isSpecial() {
        Assert.assertFalse(role.isSpecial());
    }

    @Test
    public void setSpecial() {
        boolean exp = true;
        role.setSpecial(exp);
        Assert.assertTrue(role.isSpecial());
    }

    @Test
    public void setSpecial1() {
        boolean exp = true;
        role.setSpecial(exp);
        Assert.assertTrue(role.isSpecial());
    }
}