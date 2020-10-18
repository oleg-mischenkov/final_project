package com.mischenkov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Tariff implements Serializable {

    private static final long serialVersionUID = -6243410282051145676L;
    private int id;
    private boolean active;
    private int price;
    private String title;
    private String description;
    private String shortDescription;
    private String startDate;
    private String endDate;

    public Tariff() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActive(int active) {
        if (active >= 1) {
            this.active = true;
        } else {
            this.active = false;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int pricePerWeeks(int weeks) {
        int days = weeks * 7;
        return price * days;
    }

    public double getPricePerDay() {
        if (price == 0) {
            return 0;
        } else return Math.ceil(price / 100);
    }

    public double getPricePerWeeks(int weeks) {
        int days = weeks * 7;
        if (price == 0) {
            return 0;
        } else return Math.ceil( (price / 100) * days );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return active == tariff.active &&
                price == tariff.price &&
                Objects.equals(title, tariff.title) &&
                Objects.equals(description, tariff.description) &&
                Objects.equals(shortDescription, tariff.shortDescription) &&
                Objects.equals(startDate, tariff.startDate) &&
                Objects.equals(endDate, tariff.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, price, title, description, shortDescription, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", active=" + active +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
