package ru.otus.elena.bookcatalogue.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import reactor.core.publisher.Mono;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ConfigurationProperties("application")
//@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class BookControllerRestTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testFindByGenre() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        webTestClient.get().uri("/genre?genre=e")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(BookDto.class);
    }

    @Test
    public void testFindByName() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        webTestClient.get().uri("/name?name=e")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(BookDto.class);
    }

    @Test
    public void testFindByAuthors() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        webTestClient.get().uri("/author?author=e")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(BookDto.class);
    }

    @Test
    public void testFindAll() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        webTestClient.get().uri("/findall")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(BookDto.class);
    }

    @Test
    public void testSaveBook() {
        BookDto bookdto = new BookDto("o", "o", "o");
        webTestClient.post().uri("/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(bookdto), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("o");
    }

    @Test
    public void testEditBook() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        BookDto edited = new BookDto(book.getId(), "a", "a", "a");
        webTestClient.post()
                .uri("/edit")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(edited), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    public void testDelete() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        webTestClient.get()
                .uri("/delete?id={id}", Collections.singletonMap("id", book.getId()))
                .exchange()
                .expectStatus().isOk().expectBody().isEmpty();
    }

    @Test
    public void testFindById() {
        Book book = bookRepository.save(new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        })).block();
        webTestClient.get()
                .uri("/byid?id={id}", Collections.singletonMap("id", book.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty();

    }

    @Test
    public void testGetComments() {
        Book newbook = new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        });
        newbook.addComment("comment");
        Book book = bookRepository.save(newbook).block();
        webTestClient.get().uri("/comments?id={id}", Collections.singletonMap("id", book.getId()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(String.class).hasSize(1);
    }

    @Test
    public void testAddComment() {
        Book newbook = new Book("e", "e", new ArrayList<String>() {
            {
                add("e");
            }
        });
        Book book = bookRepository.save(newbook).block();
        webTestClient.put().uri("/addcomment?comment=comment&id={id}", Collections.singletonMap("id", book.getId()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(String.class).hasSize(1);
    }
}
