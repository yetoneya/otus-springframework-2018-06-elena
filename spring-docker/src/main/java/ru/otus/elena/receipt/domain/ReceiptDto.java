package ru.otus.elena.receipt.domain;

import java.util.Objects;

public class ReceiptDto {

    private String id;
    private String type;
    private String name;
    private String component;
    private String description;
    private String url;
    private String image="";
    private String message="";

    public ReceiptDto() {
    }

    public ReceiptDto(String id, String type, String name, String component, String description, String url) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.component = component;
        this.description = description;
        this.url = url;       
    }

    public static ReceiptDto toReceiptDto(Receipt receipt) {

        return new ReceiptDto(receipt.getId(), receipt.getType(), receipt.getName(), receipt.getComponent(), receipt.getDescription(), receipt.getUrl());
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getComponent() {
        return component;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

 
    
    public void setMessage(String message) {
        this.message = message;
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
