package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import java.util.List;
import ru.otus.elena.bookcatalogue.domain.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

@RestController
@RequestMapping
public class BookControllerRest {

    private final BookRepository repository;

    @Autowired
    public BookControllerRest(BookRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody BookDto bookdto) {
        Book book = new Book(bookdto.getName(), bookdto.getGenre(), bookdto.getAuthors());
        if (!bookdto.getComment().equalsIgnoreCase("")
                && !bookdto.getComment().equalsIgnoreCase("...you comment...")) {
            book.addComment(bookdto.getComment());
        }
        repository.save(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @RequestMapping("/byid")
    public ResponseEntity<Book> findById(@RequestParam(value = "id") int id) {
        try {
            Book book = repository.findById(id).orElseThrow(NotFoundException::new);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Book> editBook(@RequestBody BookDto bookdto) {
        Book book = repository.findById(bookdto.getId()).orElseThrow(NotFoundException::new);
        book.setName(bookdto.getName());
        book.setGenre(bookdto.getGenre());
        book.setAuthors(bookdto.getAuthors());
        repository.save(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<Integer> delete(@RequestParam(value = "id") int id) {
        try {
            if (id > 0) {
                repository.deleteById(id);
            } else {
                id = -1;
            }
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.OK);
        }
    }

    @RequestMapping("/genre")
    public ResponseEntity<List<Book>> findByGenre(@RequestParam(value = "genre") String genre) {
        try {
            List<Book> list = repository.findByGenre(genre);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<Book>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/name")
    public ResponseEntity<List<Book>> findByName(@RequestParam(value = "name") String name) {
        try {
            List<Book> list = repository.findByName(name);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<Book>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/findall")
    public ResponseEntity<List<Book>> findAll() {
        try {
            List<Book> list = (List<Book>) repository.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<Book>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/comments")
    public ResponseEntity<List<String>> getComments(@RequestParam(value = "id") int id) {
        try {
            Book book = repository.findById(id).orElseThrow(NotFoundException::new);
            List<String> comments = book.getComments();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/addcomment")
    public ResponseEntity<List<String>> addComment(@RequestParam(value = "id") int id,
            @RequestParam(value = "comment") String comment) {
        try {
            Book book = repository.findById(id).orElseThrow(NotFoundException::new);
            List<String> comments = book.getComments();
            comments.add(comment);
            repository.save(book);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
}
