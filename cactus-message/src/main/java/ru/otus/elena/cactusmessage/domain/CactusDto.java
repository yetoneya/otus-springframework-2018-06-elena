package ru.otus.elena.cactusmessage.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CactusDto {
    long id;
    public String cactusname;
    public String url;

    public CactusDto() {
    }

    public CactusDto(String cactusname, String url) {
        this.cactusname = cactusname;
        this.url = url;
    }

    public CactusDto(long id, String cactusname, String url) {
        this.id = id;
        this.cactusname = cactusname;
        this.url = url;
    }

    public CactusDto toCactusDto(String name) {
        return new CactusDto(name, name.concat(".jpg"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCactusname() {
        return cactusname;
    }

    public String getUrl() {
        return url;
    }

    public void setCactusname(String cactusname) {
        this.cactusname = cactusname;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CactusDto other = (CactusDto) obj;
        return cactusname.equalsIgnoreCase(other.getCactusname()) && url.equalsIgnoreCase(other.getUrl());
    }

    @Override
    public String toString() {
        return cactusname + " " + url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cactusname, url);
    }

}
