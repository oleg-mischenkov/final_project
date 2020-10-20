package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PermissionTest {

    public Permission permission;

    @Before
    public void initPermission() {
        permission = new Permission();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, permission.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        permission.setId(exp);
        Assert.assertEquals(exp, permission.getId());
    }

    @Test
    public void isAdminAccess() {
        Assert.assertFalse(permission.isAdminAccess());
    }

    @Test
    public void setAdminAccess() {
        boolean exp = true;
        permission.setAdminAccess(exp);
        Assert.assertTrue(permission.isAdminAccess());
    }

    @Test
    public void setAdminAccess1() {
        boolean exp = true;
        permission.setAdminAccess(1);
        Assert.assertTrue(permission.isAdminAccess());
    }

    @Test
    public void isCardAccess() {
        Assert.assertFalse(permission.isCardAccess());
    }

    @Test
    public void setCardAccess() {
        boolean exp = true;
        permission.setCardAccess(exp);
        Assert.assertTrue(permission.isCardAccess());
    }

    @Test
    public void setCardAccess1() {
        boolean exp = true;
        permission.setCardAccess(1);
        Assert.assertTrue(permission.isCardAccess());
    }
}