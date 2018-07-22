
package ru.otus.elena.bookcatalogue.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="book")
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "genre")
    private String genre;
    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = Author.class,orphanRemoval = true)    
    private Collection<Author>authors;

    public Book() {
    }

    public Book(String name, String genre, Collection<Author> authors) {
        this.id=0;
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        authors.forEach(a->a.setBook(this));
    }

    public Book(String name, String genre, Collection<Author> authors, int id) {
        this.name = name;
        this.genre = genre;
        this.authors = authors;
        this.id=id;
        authors.forEach(a->a.setBook(this));
    }
    
    
    public String getName() {
        return name;
    }

   
    public String getGenre() {
        return genre;
    }

    
    public Collection<Author> getAuthors() {
        return authors;
    }


    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return Objects.equals(name, other.getName()) && 
                Objects.equals(genre, other.getGenre())&& Objects.equals(authors.toString(),other.getAuthors().toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Book: name = " + name + " genre = " + genre);
        authors.forEach(author -> builder.append(" ").append(author.toString()));
        return builder.toString();
    }

}
