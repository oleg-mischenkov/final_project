package com.mischenkov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Service implements Serializable {

    private static final long serialVersionUID = -2687066788695992892L;

    private int id;
    private boolean active;
    private String title;
    private String description;
    private String shortDescription;
    private String startDate;
    private String endDate;

    public Service() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return active == service.active &&
                Objects.equals(title, service.title) &&
                Objects.equals(description, service.description) &&
                Objects.equals(shortDescription, service.shortDescription) &&
                Objects.equals(startDate, service.startDate) &&
                Objects.equals(endDate, service.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, title, description, shortDescription, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", active=" + active +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
