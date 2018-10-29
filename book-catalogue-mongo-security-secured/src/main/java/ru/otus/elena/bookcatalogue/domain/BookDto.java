package ru.otus.elena.bookcatalogue.domain;

public class BookDto {

    private String id;
    private String name;
    private String genre;
    private String authors; 
    private String comment;

    public BookDto() {
    }

    public BookDto(String id, String name, String genre, String authors,String comment) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.authors = authors;       
        this.comment = comment;

    }

    public BookDto(String id, String name, String genre,  String authors) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.authors = authors;       
        this.comment = "...your comment...";
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setId(String id) {
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

    public static BookDto toBookDto(Book book) {
        String authors = String.join(",", book.getAuthors());    
        return new BookDto(book.getId(), book.getName(), book.getGenre(), authors);
    }

    @Override
    public String toString() {
        return name + " " + genre + " " + authors.toString();
    }

}
