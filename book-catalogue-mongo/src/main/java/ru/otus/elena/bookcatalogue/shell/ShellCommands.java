package ru.otus.elena.bookcatalogue.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

@ShellComponent
public class ShellCommands {

    private final BookRepository bookRepository;

    @Autowired
    public ShellCommands(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    @ShellMethod("Insert")
    public String insert(
            @ShellOption String name,
            @ShellOption String genre,
            @ShellOption String... authors) {
        List<String> list = Arrays.asList(authors);
        Set<String> authorSet= new HashSet<>();
        list.forEach(author -> {
            authorSet.add(author);
        });
        Book book = new Book(name, genre, authorSet);
        Book saved = bookRepository.insert(book);
        return "saved with id: " + saved.getId();
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
            @ShellOption String name) {
        
        List<Book> books = bookRepository.findByName(name);
        if(books.isEmpty()){
            return "doesn't exists";
        }
        StringBuilder builder = new StringBuilder();
        books.forEach(book
                -> {
            builder.append("book: " + book.getName() + "\n");
            Set<String> auths = book.getAuthors();
            auths.forEach(a -> builder.append("author: ").append(a).append("\n"));
            ArrayList<String> comments = book.getComments();
            builder.append("Comments:").append("\n");
            comments.forEach(c -> builder.append(c).append("\n"));
        });
        return builder.toString();
    }

    @ShellMethod("Add comment")
    public String add_comment(
            @ShellOption String name,
            @ShellOption String comment) {
        Book book = null;
        List<Book> books = bookRepository.findByName(name);
        if (books.isEmpty()) {
            return "doesn't exists";
        }
        if (books.size() == 1) {
            book=books.get(0);
        }
        if (books.size() > 1) {
            System.out.println("author?>");
            Scanner scanner = new Scanner(System.in);
            String author = scanner.nextLine();
            for (Book b : books) {
                if (b.getAuthors().contains(author)) {
                    book = b;
                }
            }
        }
        book.getComments().add(comment);
        bookRepository.save(book);
        return "comment has been added";
    }

}
