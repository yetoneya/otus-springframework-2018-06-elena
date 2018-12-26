package ru.otus.elena.cactusmessage.domain;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

public class Cactus implements Serializable {

    private long id;
    private String cactusname;
    private String url;
    private BufferedImage photo;
    

    public Cactus() {
    }

    public Cactus(String cactusname, String url, BufferedImage photo) {
        this.cactusname = cactusname;
        this.photo = photo;
        this.url = url;
    }

    public Cactus(long id, String cactusname, String url, BufferedImage photo) {
        this.id = id;
        this.cactusname = cactusname;
        this.photo = photo;
        this.url = url;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCactusname(String cactusname) {
        this.cactusname = cactusname;
    }

    public void setPhoto(BufferedImage photo) {
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public String getCactusname() {
        return cactusname;
    }

    public Image getPhoto() {
        return photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return cactusname + " " + url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cactus other = (Cactus) obj;
        return cactusname.equalsIgnoreCase(other.getCactusname()) && url.equalsIgnoreCase(other.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cactusname, url);
    }

}
