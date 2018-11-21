package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.bookcatalogue.domain.Book;

@RestController
public class BookController {

    @GetMapping("/newbooks")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            books.add(new Book("e" + i, "e" + i, new ArrayList<String>() {
                {
                    add("e");
                }
            }, false));
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
