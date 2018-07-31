 
package ru.otus.elena.bookcatalogue.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.elena.bookcatalogue.dao.BookDao;
import ru.otus.elena.bookcatalogue.domain.Author;
import ru.otus.elena.bookcatalogue.domain.Book;

@ShellComponent
public class ShellCommands {
    
    private final BookDao bookDao;
    
    @Autowired
    public ShellCommands(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    
    @ShellMethod("ReadByName")
    public String byname(
            @ShellOption String name
    ) {
        List<Book> bookList = bookDao.getByName(name);
        if (!bookList.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            bookList.forEach(book -> {
                builder.append(book.toString());
            });
            return builder.toString();
        } else {
            return "doesn't exist";
        }
    }

    @ShellMethod("Read by id")
    public String byid(
            @ShellOption int id) {
        Book book = bookDao.getById(id);
        if (book != null) {
            return book.toString();
        } else {
            return "doesn't exist";
        }
    }

    @ShellMethod("Insert")
    public String insert(
            @ShellOption String name,
            @ShellOption String genre,
            @ShellOption String... authors) {
        List<String> list = Arrays.asList(authors);
        Collection<Author> authorList = new ArrayList<>();
        list.forEach(author -> {
            authorList.add(new Author(author));
        });
        Book book = new Book(name, genre, authorList);       
        int result=bookDao.insert(book);
        return "saved with id: "+ result;
    }

    @ShellMethod("Delete")
    public String delete(@ShellOption int id) {
        int result = bookDao.delete(id);
        if (result == 1) {
            return "deleted";
        } else {
            return "has not been deleted";
        }
    }

    @ShellMethod("Read all")
    public String all() {
        List<Book> books = bookDao.getAll();
        List<String> bookList = new ArrayList<>();
        if (!books.isEmpty()) {
            books.forEach(book -> {
                bookList.add(book.toString());
            });
            return bookList.toString();
        } else {
            return "empty";
        }
    }
    
    @ShellMethod("Genre")
    public String genre(@ShellOption String genre) {
        List<Book> books = bookDao.getByGenre(genre);
        List<String> bookList = new ArrayList<>();
        if (!books.isEmpty()) {
            books.forEach(book -> {
                bookList.add(book.toString());
            });
            return bookList.toString();
        } else {
            return "empty";
        }
    }

    @ShellMethod("Count")
    public String count() {
        long result = bookDao.count();
        return String.valueOf(result);
    }
    
    @ShellMethod("Read comment")
    public String read_comment(
            @ShellOption int id) {
        Book book = bookDao.getById(id);
        if (book == null) {
            return "book doesn't exists";
        }
        StringBuilder builder = new StringBuilder("book: " + book.getName() + "\n");
        Collection<Author> auths = book.getAuthors();
        auths.forEach(a -> builder.append("author: ").append(a.getAuthorName()).append("\n"));
        Collection<String> comments = book.getComments();
        builder.append("Comments:").append("\n");
        comments.forEach(c -> builder.append(c).append("\n"));
        return builder.toString();
    }

    @ShellMethod("Add comment")
    public String add_comment(
            @ShellOption int id,
            @ShellOption String comment) {

        Book book = bookDao.getById(id);
        if (book == null) {
            return "book doesn't exists";
        }
        book.getComments().add(comment);      
        bookDao.insert(book);
        return "comment has been added";
    }


}
