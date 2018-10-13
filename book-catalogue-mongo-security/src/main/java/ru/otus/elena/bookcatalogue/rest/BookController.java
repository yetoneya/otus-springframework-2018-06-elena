package ru.otus.elena.bookcatalogue.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;
import ru.otus.elena.bookcatalogue.domain.User;
import ru.otus.elena.bookcatalogue.service.UserService;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/login")
    public String loginPage(@RequestParam Optional<String> error, Model model) {
        String err = error.orElse("privet");
        model.addAttribute("label", err);
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("label", "try");
        return "signup";
    }


    @RequestMapping("/check")
    public String check(@RequestParam String username,
            @RequestParam String password, @RequestParam String repeatpassword, Model model) {
        if (((username != "") && (password != "") && (repeatpassword != "")) && (password.equalsIgnoreCase(repeatpassword))) {
            {
                User user = userService.findByUsername(username);
                if (user != null) {
                    model.addAttribute("label", "username already exists");
                    return "signup";
                }
                Set<String> roles = new HashSet<>();
                roles.add("USER");
                userService.saveUser(username, password, roles);
                model.addAttribute("label", "Log in:");
                return "login";
            }

        } else {
            model.addAttribute("label", "invalid data");
            return "signup";
        }
    }

    @GetMapping("/logout")
    public String logOut(Model model) {
        return "login";
    }

    @RequestMapping("/books")
    public String bookList(Model model) {
        List<Book> list = (List<Book>) bookRepository.findAll();
        List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
        model.addAttribute("books", books);
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        model.addAttribute("admin", isAdmin);
        return "list";

    }

}
