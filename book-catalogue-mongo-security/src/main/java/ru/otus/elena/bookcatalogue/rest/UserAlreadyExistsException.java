package ru.otus.elena.bookcatalogue.rest;

public class UserAlreadyExistsException {
    private String message;

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
