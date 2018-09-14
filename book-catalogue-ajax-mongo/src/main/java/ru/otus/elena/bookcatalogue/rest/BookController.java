package ru.otus.elena.bookcatalogue.rest;

import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;

@Controller
public class BookController {

    private final BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String bookList(Model model) {
        List<Book> list = (List<Book>) repository.findAll();
        List<BookDto>books=list.stream().map(BookDto::toBookDto).collect(toList());
        model.addAttribute("books", books);       
        return "list";
    }

}
