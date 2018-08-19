package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

@Controller
public class BookController {

    private final BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = (List<Book>) repository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/genre")
    public String genrePage() {
        return "genre";
    }

    @GetMapping("/name")
    public String namePage() {
        return "name";
    }

    @RequestMapping("/byname")
    public String findByName(String name, Model model) {
        List<Book> books = (List<Book>) repository.findByName(name);
        model.addAttribute("books", books);
        return "list";
    }

    @RequestMapping("/bygenre")
    public String findByGenre(String genre, Model model) {
        List<Book> books = (List<Book>) repository.findByGenre(genre);
        model.addAttribute("books", books);
        return "list";
    }



    @GetMapping("/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        try {
            Book book = null;
            if (id != 0) {
                book = repository.findById(id).orElseThrow(NotFoundException::new);
            } else {
                book = new Book("name", "genre", "author");
            }
            book.addComment("...your comment...");
            model.addAttribute("book", book);
            return "edit";
        } catch (NotFoundException nfe) {
            return "notfound";
        }
    }

    @GetMapping("/comment")
    public String commentPage(@RequestParam("id") int id, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "comment";
    }

    @RequestMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book, Model model) {
        if (!book.getComments().isEmpty() && book.getId() != 0) {
            Iterator<String> itc = book.getComments().iterator();
            while (itc.hasNext()) {
                if (itc.next().equalsIgnoreCase("...your comment...")) {
                    itc.remove();
                }
            }
        }

        repository.save(book);
        List<Book> books = (List<Book>) repository.findAll();
        model.addAttribute("books", books);
        return "list";
    }
}
