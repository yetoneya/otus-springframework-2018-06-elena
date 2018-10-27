package ru.otus.elena.bookcatalogue.service;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ConfigurationProperties("application")
@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADULT"})
    public void testFindForAdult() {
        System.out.println("findForAdult");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<String>() {
            {
                add("vasya");
                add("vova");
            }
        }, true);
        bookRepository.insert(book);
        List<Book> books = bookService.findForAdult();
        assertEquals(book, books.get(0));
    }

    @Test    
    public void testFindForAll() {
        System.out.println("findForAll");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<String>() {
            {
                add("vasya");
                add("vova");
            }
        }, false);
        bookRepository.insert(book);
        List<Book> books = bookService.findForAll();
        assertEquals(book, books.get(0));
    }

}
