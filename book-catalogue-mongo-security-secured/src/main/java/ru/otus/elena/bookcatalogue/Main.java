package ru.otus.elena.bookcatalogue;

import java.time.LocalDate;
import java.time.Month;
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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.otus.elena.bookcatalogue.dao.RoleRepository;
import ru.otus.elena.bookcatalogue.domain.Role;
import ru.otus.elena.bookcatalogue.domain.User;
import ru.otus.elena.bookcatalogue.service.UserService;

@ConfigurationProperties("application")
@SpringBootApplication
@EnableMongoRepositories
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Main {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);

    }

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @PostConstruct
    public void init() {
        Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setRole("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        Role userRole = roleRepository.findByRole("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setRole("ROLE_USER");
            roleRepository.save(userRole);
        }
        Role adultRole = roleRepository.findByRole("ROLE_ADULT");
        if (adultRole == null) {
            adultRole = new Role();
            adultRole.setRole("ROLE_ADULT");
            roleRepository.save(adultRole);
        }

        User admin = userService.findByUsername("admin");
        if (admin == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);
            roles.add(adultRole);
            userService.saveUser("admin", LocalDate.of(1990, Month.MARCH, 10), "password", roles);
        }
        User user = userService.findByUsername("user");
        if (user == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(adultRole);
            userService.saveUser("user", LocalDate.of(1990, Month.MARCH, 10), "password", roles);

        }

        if (bookRepository.findByName("interactive three pigs").isEmpty()) {
            Book book1 = new Book("interactive three pigs", "fantasy", new ArrayList<String>() {
                {
                    add("volf");
                }
            }, false);
            book1.addComment("very yammy");
            book1.addComment("very nice");
            bookRepository.save(book1);
        }
        if (bookRepository.findByName("interactive kolobok").isEmpty()) {
            Book book2 = new Book("interactive kolobok", "fantasy", new ArrayList<String>() {
                {
                    add("hare");
                    add("volf");
                    add("bear");
                }
            }, true);
            book2.addComment("too dry");
            book2.addComment("too dirty");
            bookRepository.save(book2);
        }
    }
}
