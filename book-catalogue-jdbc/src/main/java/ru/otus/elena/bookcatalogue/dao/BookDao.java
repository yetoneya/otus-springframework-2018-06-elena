
package ru.otus.elena.bookcatalogue.dao;

import java.util.List;
import ru.otus.elena.bookcatalogue.domain.Book;

public interface BookDao {

    public int count();

    public long insert(Book person);

    public Book getById(long id);

    public List<Book> getAll();
    
    public int delete(long id);
    
    public List<Book> getByName(String name);
    
    public List<Book> getByGenre(String genre);
    
}
