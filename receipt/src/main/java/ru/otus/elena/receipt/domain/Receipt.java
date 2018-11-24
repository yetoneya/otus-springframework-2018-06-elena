package ru.otus.elena.receipt.domain;

import java.io.Serializable;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "receipt")
public class Receipt implements Serializable {

    @Id
    private String id;
    private String type;
    private String name;
    private String component;
    private String description;

    public Receipt() {
    }

    public Receipt(String type, String name, String component, String description) {
        this.type = type;
        this.name = name;
        this.component = component;
        this.description = description;       
    }

    public Receipt(String id, String type, String name, String component, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.component = component;
        this.description = description;       
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComponent(String components) {
        this.component = components;
    }

    public String getComponent() {
        return component;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Receipt other = (Receipt) obj;
        return Objects.equals(type, other.getType())
                && Objects.equals(name, other.getName())
                && Objects.equals(component, other.getComponent())
                && Objects.equals(description, other.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, component, description);
    }

    @Override
    public String toString() {
        return type + " " + name + " " + component;
    }

}
