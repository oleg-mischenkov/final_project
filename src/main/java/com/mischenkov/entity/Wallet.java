package com.mischenkov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Wallet implements Serializable {

    private static final long serialVersionUID = -1818717697831626018L;

    private int id;
    private int userId;
    private int money;
    private int blockedMoney;

    public Wallet() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBlockedMoney() {
        return blockedMoney;
    }

    public void setBlockedMoney(int blockedMoney) {
        this.blockedMoney = blockedMoney;
    }

    public double getMoneyInDouble() {
        if (money == 0) {
            return 0;
        } else return Math.ceil(money / 100);
    }

    public double getBlockedMoneyInDouble() {
        if (money == 0) {
            return 0;
        } else return Math.ceil(blockedMoney / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return userId == wallet.userId &&
                money == wallet.money &&
                blockedMoney == wallet.blockedMoney;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, money, blockedMoney);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", userId=" + userId +
                ", money=" + money +
                ", blockedMoney=" + blockedMoney +
                '}';
    }
}
