package ru.otus.elena.bookcatalogue.rest;

import io.micrometer.core.instrument.Counter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.service.ExternalService;
import ru.otus.elena.bookcatalogue.service.UserService;

@RestController
public class AdminControllerRest {

    Logger logger = LoggerFactory.getLogger(AdminControllerRest.class);

    @Autowired
    private Counter warnCounter;
    @Autowired
    private UserService userService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ExternalService externalService;

    @RequestMapping("/user/delete")
    public ResponseEntity<List<String>> deleteUser(@RequestParam(value = "id") String id) {
        try {
            userService.deleteUserById(id);
            logger.info("userDelete was successful");
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("exception in deleteUser");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/user/refresh")
    public ResponseEntity<List<String>> refreshUser(@RequestParam(value = "id") String id) {
        try {
            userService.refreshUser(id);
            logger.info("refreshUser was successful");
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("exception in refreshUser");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @GetMapping("/addfrom")
    public ResponseEntity<List<String>> addBooksFromExternalSource() {
        List<Book> newBooks = new ArrayList<>();
        newBooks.addAll(externalService.getBooks());
        try {
            if (!newBooks.isEmpty()) {
                List<Book> list = bookRepository.saveAll(newBooks);
                String success = list.size() + " new books have been saved";
                logger.info(success);
                return new ResponseEntity<>(new ArrayList<String>() {
                    {
                        add(success);
                    }
                }, HttpStatus.OK);
            } else {
                String failure = "new books are not available now, let contact later";
                logger.info(failure);
                return new ResponseEntity<>(new ArrayList<String>() {
                    {
                        add(failure);
                    }
                }, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in addBooksFromExternalSource: " + e.getMessage());
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

}
