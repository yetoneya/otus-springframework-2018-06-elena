package ru.otus.elena.bookcatalogue.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ConfigurationProperties("application")
@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class BookControllerRestTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository repository;
    Book book;

    public BookControllerRestTest() {
    }


    @Before
    public void setUp() {
        repository.deleteAll();
        book = new Book("e", "e", new ArrayList<String>(){{add("e");}});
        repository.save(book);
    }

    @Test
    public void saveBookTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        BookDto bookDto = new BookDto("", "", "", "");
        this.mvc.perform(MockMvcRequestBuilders.post("/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));

    }

    @Test
    public void editBookTest() throws Exception {
        BookDto bookDto = new BookDto(book.getId(),"", "", "");
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(MockMvcRequestBuilders.post("/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    public void deleteTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/delete").param("id", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    public void findByIdTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/byid").param("id", book.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    public void findByGenreTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/genre?genre=e")).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }
    
    @Test
    public void findByNameTest() throws Exception {
         MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/name?name=e")).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }
    
        @Test
    public void findByAuthorTest() throws Exception {
         MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/author?author=e")).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    public void findAllTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/findall")).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    public void getCommentsTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/comments").param("id",book.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    public void addCommentTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/addcomment").param("id", book.getId()).param("comment", "my comment")).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }
}