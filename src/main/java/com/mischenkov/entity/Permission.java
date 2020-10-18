package com.mischenkov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Permission implements Serializable {

    private static final long serialVersionUID = 1054883829583425180L;

    private int id;
    private boolean adminAccess;
    private boolean cardAccess;

    public Permission() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    public void setAdminAccess(int adminAccess) {
        if (adminAccess >= 1) {
            this.adminAccess = true;
        } else {
            this.adminAccess = false;
        }
    }

    public boolean isCardAccess() {
        return cardAccess;
    }

    public void setCardAccess(boolean cardAccess) {
        this.cardAccess = cardAccess;
    }

    public void setCardAccess(int cardAccess) {
        if (cardAccess >= 1) {
            this.cardAccess = true;
        } else {
            this.cardAccess = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return adminAccess == that.adminAccess &&
                cardAccess == that.cardAccess;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminAccess, cardAccess);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", adminAccess=" + adminAccess +
                ", cardAccess=" + cardAccess +
                '}';
    }
}
