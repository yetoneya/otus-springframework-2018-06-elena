package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.elena.bookcatalogue.domain.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

@RestController
@RequestMapping
public class BookControllerRest {

    private final BookRepository bookRepository;

    @Autowired
    public BookControllerRest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/save")
    public Mono<BookDto> saveBook(@RequestBody BookDto bookDto) {
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(bookDto.getAuthors().split(",")));
        Book book = new Book(bookDto.getName(), bookDto.getGenre(), authors);
        return bookRepository.save(book).map(BookDto::toBookDto);

    }

    @PostMapping("/edit")
    public Mono<BookDto> editBook(@RequestBody BookDto bookDto) {
        return bookRepository.findById(bookDto.getId())
                .flatMap(book -> {
                    book.setName(bookDto.getName());
                    book.setGenre(bookDto.getGenre());
                    ArrayList<String> authors = new ArrayList<>(Arrays.asList(bookDto.getAuthors().split(",")));
                    book.setAuthors(authors);
                    return bookRepository.save(book);
                }).map(BookDto::toBookDto);

    }

    @RequestMapping("/byid")
    public Mono<ResponseEntity<BookDto>> findById(@RequestParam(value = "id") String id) {
        return bookRepository.findById(id).map(BookDto::toBookDto).map(savedBook -> ResponseEntity.ok(savedBook))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @RequestMapping("/delete")
    public Mono<ResponseEntity<Void>> delete(@RequestParam(value = "id") String id) {
        return bookRepository.findById(id)
                .flatMap(existingBook
                        -> bookRepository.delete(existingBook)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping("/genre")
    public Flux<BookDto> findByGenre(@RequestParam(value = "genre") String genre) {
        return bookRepository.findByGenre(genre).map(BookDto::toBookDto).sort();

    }

    @RequestMapping("/name")
    public Flux<BookDto> findByName(@RequestParam(value = "name") String name) {
        return bookRepository.findByName(name).map(BookDto::toBookDto).sort();

    }

    @RequestMapping("/author")
    public Flux<BookDto> findByAuthors(@RequestParam(value = "author") String author) {
        return bookRepository.findByAuthors(author).map(BookDto::toBookDto).sort();

    }

    @RequestMapping("/findall")
    public Flux<BookDto> findAll() {
        return bookRepository.findAll().map(BookDto::toBookDto).sort();

    }

    @RequestMapping("/comments")
    public Mono<List<String>> getComments(@RequestParam(value = "id") String id) {
        return bookRepository.findById(id).map(book -> book.getComments());

    }

    @RequestMapping("/addcomment")
    public Mono<List<String>> addComment(@RequestParam(value = "id") String id,
            @RequestParam(value = "comment") String comment) {
        return bookRepository.findById(id).flatMap(book -> {
            book.getComments().add(comment);
            return bookRepository.save(book);
        }).map(book -> book.getComments());

    }
}
