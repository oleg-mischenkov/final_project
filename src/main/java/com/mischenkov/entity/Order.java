package com.mischenkov.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = -5119285389542056852L;

    private int id;
    private List<Tariff> itemList;
    private int total;

    public Order() {
        itemList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tariff> getItemList() {
        return itemList;
    }

    public void setItemList(List<Tariff> itemList) {
        this.itemList = itemList;
    }

    public boolean isHasItem(int itemId) {
        boolean result = false;

        for (Tariff tariff: itemList) {
            if (itemId == tariff.getId()) {
                result = true;
                break;
            }
        }

        return result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getTotalInDouble() {
        return Math.ceil(total / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return total == order.total &&
                Objects.equals(itemList, order.itemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemList, total);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemList=" + itemList +
                ", total=" + total +
                '}';
    }
}
