package ru.otus.elena.bookcatalogue.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    public List<Book> findByName(String name);

    public List<Book> findByGenre(String genre);

}
