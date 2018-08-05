
package ru.otus.elena.bookcatalogue.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.bookcatalogue.domain.Author;
import ru.otus.elena.bookcatalogue.domain.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED+"=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED+"=false"})
public class BookDaoJdbcTest {
    
    @Autowired
    private BookDaoJdbc bookDao;
    
    public BookDaoJdbcTest() {
    }

    @Test
    public void testCount() {      
       System.out.println("test count");
       int size= bookDao.getAll().size();
       int count=bookDao.count();
       assertEquals(size,count);
    }
 
    @Test
    public void testInsert() {
        System.out.println("test insert");
        Book book=new Book("masha_v_derevne", "fantasy", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id=bookDao.insert(book);
        Book rebook=bookDao.getById(id);
        assertEquals(book,rebook);
    }

    @Test
    public void testGetById() {
        System.out.println("test getById");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id = bookDao.insert(book);
        Book rebook=bookDao.getById(id);
        assertEquals(book,rebook);
    }

    @Test
    public void testGetAll() {
        System.out.println("test getAll");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id1 = bookDao.insert(book);
        Book otherBook = new Book("masha_na_paseke", "horror", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id2 = bookDao.insert(otherBook);
        List<Book>list=bookDao.getAll();
        int counter=0;
        for(Book b:list){
            if(b.getId()==id1||b.getId()==id2){
                counter++;
            }
        }
        
        assertEquals(String.valueOf(counter),"2");
    }
     
    @Test
    public void testDelete() {
        System.out.println("test delete");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id = bookDao.insert(book);
        bookDao.delete(id);
        Book rebook=bookDao.getById(id);
        Book nullBook=null;
        assertEquals(rebook,nullBook);
        
    } 
     
    @Test
    public void testGetByName() {
        System.out.println("test getByName");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id = bookDao.insert(book);
        List<Book> list = bookDao.getByName("masha_v_derevne");
        boolean coincidence = false;
        for (Book b : list) {
            if (b.equals(book)) {
                coincidence = true;
                break;
            }
        }
        assertEquals(coincidence, true);
    }

    @Test
    public void testGetByGenre() {
        System.out.println("test getByGenre");
        Book book = new Book("masha_v_derevne", "fantasy", new ArrayList<Author>() {
            {
                add(new Author("vasya"));
                add(new Author("vova"));
            }
        });
        long id = bookDao.insert(book);
        List<Book> list = bookDao.getByGenre("fantasy");
        boolean coincidence=false;
        for(Book b:list){
            if(b.equals(book)){
                coincidence=true;
                break;
            }
        }
       assertEquals(coincidence,true);
    }
    
}
