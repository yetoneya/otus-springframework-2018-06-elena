
package ru.otus.elena.bookcatalogue.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="author")
public class Author implements Serializable{
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "authorname")
    private String authorName;


    public Author() {
    }

    public Author(String authorName) {
        this.id=0;
        this.authorName = authorName;
    }

    public Author(String authorName, int id) {
        this.id = id;
        this.authorName = authorName;
    }

    
    public String getAuthorName() {
        return authorName;
    }


    public int getId() {
        return id;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    

    @Override
    public String toString() {
        return "author = " + authorName;
    }


}
