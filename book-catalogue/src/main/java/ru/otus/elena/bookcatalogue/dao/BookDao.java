
package ru.otus.elena.bookcatalogue.dao;

import java.util.List;
import ru.otus.elena.bookcatalogue.domain.Book;

public interface BookDao {

    public long count();

    public int insert(Book book);

    public Book getById(int id);

    public List<Book> getAll();
    
    public int delete(int id);
    
    public List<Book> getByName(String name);
    
    public List<Book> getByGenre(String genre);
    
}
