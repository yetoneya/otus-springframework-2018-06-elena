package ru.otus.elena.bookcatalogue.rest;

public class NotFoundException extends RuntimeException{
    private String message;
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
