package ru.otus.elena.bookcatalogue.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.BookDto;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String bookList(Model model) {
        List<BookDto>books=bookRepository.findAll().map(BookDto::toBookDto).collectSortedList().block();      
        model.addAttribute("books", books);       
        return "list";
    }

}
