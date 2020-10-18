package com.mischenkov.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Basket implements Serializable {

    private static final long serialVersionUID = 2605619893334660001L;
    private List<Tariff> itemList;

    public Basket() {
        itemList = new ArrayList<>();
    }

    public int size() {
        return itemList.size();
    }

    public void add(Tariff tariff) {
        itemList.add(tariff);
    }

    public List<Tariff> getAll() {
        return itemList;
    }

    public int totalMoney() {
        int summ = 0;

        for (Tariff element: itemList) {
            summ += element.getPrice();
        }

        return summ;
    }

    public int totalMoneyPerWeek(int week) {
        int days = week * 7;
        return totalMoney() * days;
    }

    public double totalMoneyPerWeekInDouble(int week) {
        return Math.ceil( totalMoneyPerWeek(week) / 100);
    }

    public double totalMoneyInDouble() {
        int total = totalMoney();

        if (total == 0) {
            return 0.0D;
        } return Math.ceil( total / 100 );
    }

    public boolean isHasTariff(int tariffId) {
        boolean result = false;

        for (Tariff element: itemList) {
            if ( tariffId == element.getId() ) {
                return true;
            }
        }

        return result;
    }

    public void deleteId(int tariffId) {
        itemList = itemList.stream()
                .filter( tariff -> tariff.getId() != tariffId )
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(itemList, basket.itemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemList);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "itemList=" + itemList +
                '}';
    }
}
