package ru.otus.elena.bookcatalogue.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Book implements Serializable {

    @Id private String id;   
    private String name;    
    private String genre;
    private Set<String> authors;    
    private ArrayList<String> comments;

    public Book() {
    }

    public Book(String name, String genre, Set<String> authors) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        comments = new ArrayList<>();
    }

    public Book(String name, String genre, Set<String> authors, String id) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.id = id;
        comments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    
    public Set<String> getAuthors() {
        return authors;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
    
    public String getId() {
        return id;
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
        StringBuilder builder = new StringBuilder("Book: name = " + name + " genre = " + genre+" ");
        builder.append("authors: ");
        authors.forEach(author -> builder.append(" ").append(author));
        builder.append(" id=").append(id).append("\n");
        return builder.toString();
    }

}
