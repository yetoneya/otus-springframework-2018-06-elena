package ru.otus.elena.bookcatalogue.rest;

import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;
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

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("/books")
    public String bookList(Model model) {
        List<Book> list = (List<Book>) bookService.findForAll();
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
        return "list";

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
            return "list";
        } catch (Exception e) {
            model.addAttribute("label", "Log in:");
            return "login"; 
        }

    }
}
