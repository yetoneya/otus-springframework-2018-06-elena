package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    // @Autowired
    // private WebTestClient webClient;
    @Autowired
    private BookRepository repository;

    @Test
    public void bookPageTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void genreTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/genre")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void nameTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/name")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void findByNameTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/byname?name=name")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void findByGenreTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/bygenre?genre=genre")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }



    @Test
    public void editPageTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/edit?id=1")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void commentPageTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/comment?id=1")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void savePageTest() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        //mockMvc.perform(post("/somehwere/new").param("items[0].value","value"));
        Book book=new Book("","","");
        mvc.perform(MockMvcRequestBuilders.post("/save")
                .param("name","name")
                .param("genre","genre")
                .param("author","author")
                .param("comments[0]","comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
                
                
              
    }
}
/*    private MockMvc mockMvc;
 
    //Add setUp() method here
 
    @Test
    @ExpectedDatabase("toDoData.xml")
    public void addTodoWhenTitleAndDescriptionAreTooLong() throws Exception {
        String title = TodoTestUtil.createStringWithLength(101);
        String description = TodoTestUtil.createStringWithLength(501);
 
        mockMvc.perform(post("/todo/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", description)
                .param("title", title)
                .sessionAttr("todo", new TodoDTO())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("todo/add"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/add.jsp"))
                .andExpect(model().attributeHasFieldErrors("todo", "title"))
                .andExpect(model().attributeHasFieldErrors("todo", "description"))
                .andExpect(model().attribute("todo", hasProperty("id", nullValue())))
                .andExpect(model().attribute("todo", hasProperty("description", is(description))))
                .andExpect(model().attribute("todo", hasProperty("title", is(title))));
    }
}
*/