package ru.otus.elena.cactuscatalogue.domain;

import java.io.Serializable;
import java.util.Objects;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cactus")
public class Cactus implements Serializable {

    @org.springframework.data.annotation.Id
    private long id;

    @Indexed(unique = true)
    private String cactusname;

    private long size;

    public Cactus() {
    }

    public Cactus(String cactusname, long size) {
        this.cactusname = cactusname;
        this.size = size;       
    }

    public Cactus(long id, String cactusname, long size) {
        this.id = id;
        this.cactusname = cactusname;       
        this.size = size;
    }

    public void onBirthDay() {
        ++size;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCactusname(String cactusname) {
        this.cactusname = cactusname;
    }

    public void setSize(long size) {
        this.size = size;
    }


    public long getId() {
        return id;
    }

    public String getCactusname() {
        return cactusname;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return cactusname + " " + size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cactus other = (Cactus) obj;
        return cactusname.equalsIgnoreCase(other.getCactusname())               
                && size == other.getSize();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cactusname, size);
    }

}
