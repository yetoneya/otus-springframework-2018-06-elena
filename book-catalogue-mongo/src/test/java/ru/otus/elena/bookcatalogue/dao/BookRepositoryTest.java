package ru.otus.elena.bookcatalogue.dao;

import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.bookcatalogue.domain.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED+"=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED+"=false"})
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Before
    public void setUp() {
    }


    @Test
    public void testFindByName() {
        System.out.println("findByName");
        Book book = new Book("masha_v_derevne", "fantasy", new HashSet<String>() {
            {
                add("vasya");
                add("vova");
            }
        });
        repository.insert(book);
        Book rebook = repository.findByName("masha_v_derevne").get(0);
        assertEquals(book, rebook);

    }

    @Test
    public void testFindByGenre() {
        System.out.println("findByGenre");
        Book book = new Book("masha_v_derevne", "fantasy", new HashSet<String>() {
            {
                add("vasya");
                add("vova");
            }
        });
        repository.insert(book);
        Book rebook = repository.findByGenre("fantasy").get(0);
        assertEquals(book, rebook);
    }

}
