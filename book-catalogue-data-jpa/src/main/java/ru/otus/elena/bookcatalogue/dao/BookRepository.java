package ru.otus.elena.bookcatalogue.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

    public List<Book> findByName(String name);

    public List<Book> findByGenre(String genre);

}
