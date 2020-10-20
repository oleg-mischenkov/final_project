package com.mischenkov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    public User user;

    @Before
    public void initUser() {
        user = new User();
    }

    @Test
    public void getId() {
        int exp = 0;
        Assert.assertEquals(exp, user.getId());
    }

    @Test
    public void setId() {
        int exp = 10;
        user.setId(exp);
        Assert.assertEquals(exp, user.getId());
    }

    @Test
    public void getLogin() {
        String expLogin = "Mike";
        user.setLogin(expLogin);
        Assert.assertEquals(expLogin, user.getLogin());
    }

    @Test
    public void setLogin() {
        String expLogin = "Bob";
        user.setLogin(expLogin);
        Assert.assertEquals(expLogin, user.getLogin());
    }

    @Test
    public void getEmail() {
        String expMail = "some@gmail.com";
        user.setEmail(expMail);
        Assert.assertEquals(expMail, user.getEmail());
    }

    @Test
    public void setEmail() {
        String expMail = "bob@gmail.com";
        user.setEmail(expMail);
        Assert.assertEquals(expMail, user.getEmail());
    }

    @Test
    public void getPassword() {
        String psw = "asdfqwerqwersadf234";
        user.setPassword(psw);
        Assert.assertEquals(psw, user.getPassword());
    }

    @Test
    public void setPassword() {
        String psw = "1234";
        user.setPassword(psw);
        Assert.assertEquals(psw, user.getPassword());
    }

    @Test
    public void getFirstName() {
        String first = "Buba";
        user.setFirstName(first);
        Assert.assertEquals(first, user.getFirstName());
    }

    @Test
    public void setFirstName() {
        String first = "Eva";
        user.setFirstName(first);
        Assert.assertEquals(first, user.getFirstName());
    }

    @Test
    public void getLastName() {
        String last = "Eva";
        user.setLastName(last);
        Assert.assertEquals(last, user.getLastName());
    }

    @Test
    public void setLastName() {
        String last = "Eva";
        user.setLastName(last);
        Assert.assertEquals(last, user.getLastName());
    }

    @Test
    public void getCreateTime() {
        String date = "2020-11-10";
        user.setCreateTime(date);
        Assert.assertEquals(date, user.getCreateTime());
    }

    @Test
    public void setCreateTime() {
        String date = "2020-10-10";
        user.setCreateTime(date);
        Assert.assertEquals(date, user.getCreateTime());
    }

    @Test
    public void getGender() {
        User.Gender gender = User.Gender.F;
        user.setGender(gender);
        Assert.assertEquals(gender, user.getGender());
    }

    @Test
    public void setGender() {
        User.Gender gender = User.Gender.M;
        user.setGender(gender);
        Assert.assertEquals(gender, user.getGender());
    }

    @Test
    public void setGender1() {
        User.Gender gender = User.Gender.M;
        user.setGender(gender);
        Assert.assertEquals(gender, user.getGender());
    }

    @Test
    public void isActive() {
        boolean active = false;
        user.setActive(active);
        Assert.assertFalse(user.isActive());
    }

    @Test
    public void setActive() {
        boolean active = false;
        user.setActive(active);
        Assert.assertFalse(user.isActive());
    }

    @Test
    public void setActive1() {
    }
}