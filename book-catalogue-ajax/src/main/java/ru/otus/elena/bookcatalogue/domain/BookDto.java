package ru.otus.elena.bookcatalogue.domain;

public class BookDto {

    private int id;
    private String name;
    private String genre;
    private String authors;
    private String comment;

    public BookDto() {
    }

    public BookDto(int id, String name, String genre, String authors, String comment) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.comment = comment;
        this.id = id;
    }

    public BookDto(String name, String genre, String authors, String comment) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.comment = comment;
    }

    public BookDto(int id, String name, String genre, String authors) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.comment = "...your comment...";
        this.id = id;
    }

    public BookDto(String name, String genre, String authors) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.comment = "...your comment...";
    }

    public String getAuthors() {
        return authors;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return name + " " + genre + " " + authors;
    }

}
