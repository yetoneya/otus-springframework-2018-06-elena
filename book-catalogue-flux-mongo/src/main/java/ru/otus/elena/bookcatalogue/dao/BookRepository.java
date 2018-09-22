package ru.otus.elena.bookcatalogue.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import ru.otus.elena.bookcatalogue.domain.Book;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {

    Flux<Book> findByName(String name);

    public Flux<Book> findByGenre(String genre);

    @Query(value = "{'author': {$regex : ?0}}")
    public Flux<Book> findByAuthors(String author);

}
