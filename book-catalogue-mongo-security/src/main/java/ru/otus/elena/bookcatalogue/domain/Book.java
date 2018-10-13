package ru.otus.elena.bookcatalogue.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="book")
public class Book implements Serializable {

    @Id
    private String id;
    private String name;
    private String genre;
    private ArrayList<String> authors;
    private ArrayList<String> comments;

    public Book() {
    }

    public Book(String name, String genre, ArrayList<String> authors) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        comments = new ArrayList<>();
    }

    public Book(String id, String name, String genre, ArrayList<String> authors) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.id = id;
        comments = new ArrayList<>();
    }

    public Book(String id, String name, String genre, ArrayList<String> authors, ArrayList<String> comments) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.comments = new ArrayList<>();
        this.comments.addAll(comments);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return Objects.equals(name, other.getName())
                && Objects.equals(genre, other.getGenre()) && Objects.equals(authors.toString(), other.getAuthors().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, authors);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Book: name = " + name + " genre = " + genre + " ");
        builder.append("authors: ");
        authors.forEach(author -> builder.append(" ").append(author));
        builder.append(" id=").append(id).append("\n");
        return builder.toString();
    }

}
