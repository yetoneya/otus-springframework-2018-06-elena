package ru.otus.elena.bookcatalogue.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.elena.bookcatalogue.domain.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    public List<Book> findByName(String name);

    public List<Book> findByGenre(String genre);

    @Query(value = "{'authors': {$regex : ?0}}")
    public List<Book> findByAuthors(String authors);

}
