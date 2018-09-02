package ru.otus.elena.bookcatalogue.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.bookcatalogue.domain.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository repository;

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void testExample() throws Exception {
        Book book = new Book("masha_v_derevne", "fantasy", "petrov, sidorov");
        entityManager.persist(book);
        Book rebook = repository.findById(3).get();
        assertEquals(book, rebook);
    }

    @Test
    public void testFindByName() {
        System.out.println("findByName");
        Book book = new Book("masha_v_derevne", "fantasy", "petrov, sidorov");
        entityManager.persist(book);
        Book rebook = repository.findByName("masha_v_derevne").get(0);
        assertEquals(book, rebook);

    }

    @Test
    public void testFindByGenre() {
        System.out.println("findByGenre");
        Book book = new Book("masha_v_derevne", "fantasy", "petrov, sidorov");
        entityManager.persist(book);
        Book rebook = repository.findByGenre("fantasy").get(0);
        assertEquals(book, rebook);
    }

}
