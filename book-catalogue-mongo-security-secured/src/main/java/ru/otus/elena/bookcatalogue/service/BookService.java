package ru.otus.elena.bookcatalogue.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    
    public List<Book> findForAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().filter(book -> !book.isForAdult()).collect(Collectors.toList());
    }

    @Secured("ROLE_ADULT")
    public List<Book> findForAdult() {
        List<Book> books = bookRepository.findAll();
        return books;
    }
}
