package com.mischenkov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private static final long serialVersionUID = 7886294885874600029L;

    private int id;
    private int money;
    private int code;

    public Card() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return money == card.money &&
                code == card.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, code);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", money=" + money +
                ", code=" + code +
                '}';
    }
}
