package com.mischenkov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Language implements Serializable {

    private static final long serialVersionUID = 803374923902006643L;

    private int id;
    private String title;
    private boolean active;
    private String iso639_1;
    private String iso639_2;
    private String iso639_3;
    private int code;

    public Language() {
        // NOP
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getIso639_1() {
        return iso639_1;
    }

    public void setIso639_1(String iso639_1) {
        this.iso639_1 = iso639_1;
    }

    public String getIso639_2() {
        return iso639_2;
    }

    public void setIso639_2(String iso639_2) {
        this.iso639_2 = iso639_2;
    }

    public String getIso639_3() {
        return iso639_3;
    }

    public void setIso639_3(String iso639_3) {
        this.iso639_3 = iso639_3;
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
        Language language = (Language) o;
        return id == language.id &&
                active == language.active &&
                code == language.code &&
                Objects.equals(title, language.title) &&
                Objects.equals(iso639_1, language.iso639_1) &&
                Objects.equals(iso639_2, language.iso639_2) &&
                Objects.equals(iso639_3, language.iso639_3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, active, iso639_1, iso639_2, iso639_3, code);
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", active=" + active +
                ", iso639_1='" + iso639_1 + '\'' +
                ", iso639_2='" + iso639_2 + '\'' +
                ", iso639_3='" + iso639_3 + '\'' +
                ", code=" + code +
                '}';
    }
}
