
package ru.otus.elena.bookcatalogue.domain;

import java.util.ArrayList;
import java.util.Objects;
import ru.otus.elena.bookcatalogue.dataset.DataSet;

public class Book extends DataSet{
    private String name;
    private String genre;
    private ArrayList<Author>authors;

    public Book(String name, String genre, ArrayList<Author> authors) {
        super(0);
        this.name = name;
        this.genre = genre;
        this.authors = authors;
    }

    public Book(String name, String genre, ArrayList<Author> authors, long id) {
        super(id);
        this.name = name;
        this.genre = genre;
        this.authors = authors;
    }
    
    

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }
     

    @Override
    public long getId() {
        return super.getId();
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
