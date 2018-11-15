package ru.otus.elena.bookcatalogue.rest;

import io.micrometer.core.instrument.Counter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;
import ru.otus.elena.bookcatalogue.service.BookService;
import ru.otus.elena.bookcatalogue.service.UserService;

@Controller
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private Counter loginCounter;
    @Autowired
    private Counter errorCounter;
    @Autowired
    private Counter warnCounter;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @RequestMapping("/books")
    public String bookList(Model model) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority authority : authorities) {
            String au = authority.getAuthority();
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        model.addAttribute("admin", isAdmin);
        try {
            List<Book> list = (List<Book>) bookService.findForAll();
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            model.addAttribute("books", books);
            logger.info("bookList was successful");
            return "list";

        } catch (Exception e) {
            logger.error("error in bookList");
            errorCounter.increment();
            model.addAttribute("books", new ArrayList<BookDto>());
            return "list";
        }
    }

    @RequestMapping("/foradult")
    public String bookForAdult(Model model) {
        try {
            List<Book> list = (List<Book>) bookService.findForAdult();
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            model.addAttribute("books", books);
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            boolean isAdmin = false;
            for (GrantedAuthority authority : authorities) {
                String au = authority.getAuthority();
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
            model.addAttribute("admin", isAdmin);
            logger.info("bookForAdult was successful");
            return "list";
        } catch (Exception e) {
            logger.error("error in bookForAdult");
            errorCounter.increment();
            model.addAttribute("label", "Log in:");
            return "login";
        }

    }
}
