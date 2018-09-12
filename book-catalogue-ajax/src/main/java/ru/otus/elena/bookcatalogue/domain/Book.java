package ru.otus.elena.bookcatalogue.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "genre")
    private String genre;
    @Column(name = "authors")
    private String authors;
    @Basic
    @Column(name = "comments")
    private ArrayList<String> comments;

    public Book() {
    }

    public Book(String name, String genre, String authors) {
        this.name = name;
        this.genre = genre;
        this.comments = new ArrayList<>();
        this.authors = authors;

    }

    public Book(int id,String name, String genre, String authors) {
        this.name = name;
        this.genre = genre;
        this.id = id;
        this.comments = new ArrayList<>();
        this.authors = authors;
    }

    public Book(int id, String name, String genre, String authors, ArrayList<String> comments) {
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        comments = new ArrayList<>();
        this.comments.addAll(comments);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addComment(String comment) {
        comments.add(comment);
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

        StringBuilder builder = new StringBuilder("Book: name = " + name + " genre = " + genre);
        //authors.forEach(author -> builder.append(" ").append(author));
        builder.append(" id=").append(id);
        return builder.toString();
    }

}
