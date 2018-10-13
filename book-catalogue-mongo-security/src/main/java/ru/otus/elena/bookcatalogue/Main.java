package ru.otus.elena.bookcatalogue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.otus.elena.bookcatalogue.domain.User;
import ru.otus.elena.bookcatalogue.service.UserService;

@ConfigurationProperties("application")
@SpringBootApplication
@EnableMongoRepositories
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Main {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserService userService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);

    }

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @PostConstruct
    public void init() {

        User admin = userService.findByUsername("admin");
        if (admin == null) {
            Set<String> roles = new HashSet<>();
            roles.add("ADMIN");
            roles.add("USER");
            userService.saveUser("admin", "password", roles);

        }
        User user = userService.findByUsername("user");
        if (user == null) {
            Set<String> roles = new HashSet<>();
            roles.add("USER");
            userService.saveUser("user", "password", roles);

        }

        if (bookRepository.findByName("interactive three pigs").isEmpty()) {
            Book book1 = new Book("interactive three pigs", "fantasy", new ArrayList<String>() {
                {
                    add("volf");
                }
            });
            book1.addComment("very yammy");
            book1.addComment("very nice");
            bookRepository.save(book1);
        }
        if (bookRepository.findByName("interactive three pigs").isEmpty()) {
            Book book2 = new Book("interactive kolobok", "fantasy", new ArrayList<String>() {
                {
                    add("hare");
                    add("volf");
                    add("bear");
                }
            });
            book2.addComment("too dry");
            book2.addComment("too dirty");
            bookRepository.save(book2);
        }
    }
}
