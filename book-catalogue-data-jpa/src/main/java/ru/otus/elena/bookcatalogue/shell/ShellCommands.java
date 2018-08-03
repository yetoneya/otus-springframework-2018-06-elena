 
package ru.otus.elena.bookcatalogue.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.elena.bookcatalogue.dao.BookRepository;

import ru.otus.elena.bookcatalogue.domain.Author;
import ru.otus.elena.bookcatalogue.domain.Book;

@ShellComponent
public class ShellCommands {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public ShellCommands(BookRepository bookDao) {
        this.bookRepository = bookDao;
    }
    
    @ShellMethod("ReadByName")
    public String byname(
            @ShellOption String name
    ) {
        List<Book> bookList = bookRepository.findByName(name);
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
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get().toString();
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
        Book saved=bookRepository.save(book);
        return "saved with id: "+ saved.getId();
    }

    @ShellMethod("Delete")
    public String delete(@ShellOption int id) {
        bookRepository.deleteById(id);
        return "deleted";
    }

    @ShellMethod("Read all")
    public String all() {
        List<Book> books = (List<Book>) bookRepository.findAll();
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
        List<Book> books = bookRepository.findByGenre(genre);
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
        long result = bookRepository.count();
        return String.valueOf(result);
    }
    
    @ShellMethod("Read comment")
    public String read_comment(
            @ShellOption int id) {
        Book book=null;
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
        book=optionalBook.get();
        } else {
            return "doesn't exist";
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
        Book book=null;
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
        book=optionalBook.get();
        } else {
            return "doesn't exist";
        }
        book.getComments().add(comment);      
        bookRepository.save(book);
        return "comment has been added";
    }


}
