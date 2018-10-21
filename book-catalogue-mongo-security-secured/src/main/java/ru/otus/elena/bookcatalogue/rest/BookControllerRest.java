package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;

@RestController
@RequestMapping
public class BookControllerRest {

    private final BookRepository bookRepository;

    @Autowired
    public BookControllerRest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping("/save")
    public ResponseEntity<BookDto> saveBook(@RequestParam String name, 
            @RequestParam String genre, @RequestParam String authors, @RequestParam Boolean adult) {
        ArrayList<String> authorList = new ArrayList<>(Arrays.asList(authors.split(",")));
        
        Book book = new Book(name, genre, authorList, adult);
        bookRepository.save(book);
        BookDto bookdto=BookDto.toBookDto(book);
        return new ResponseEntity<BookDto>(bookdto, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<BookDto> editBook(@RequestBody BookDto bookDto) {
        try {
            Book book = bookRepository.findById(bookDto.getId()).orElseThrow(NotFoundException::new);
            book.setName(bookDto.getName());
            book.setGenre(bookDto.getGenre());
            ArrayList<String> authors = new ArrayList<>(Arrays.asList(bookDto.getAuthors().split(",")));
            book.setAuthors(authors);
            bookRepository.save(book);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(new BookDto("", "", "", ""), HttpStatus.OK);
        }
    }

    @RequestMapping("/byid")
    public ResponseEntity<BookDto> findById(@RequestParam(value = "id") String id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
            BookDto bookDto = BookDto.toBookDto(book);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BookDto("", "", "", ""), HttpStatus.OK);
        }
    }

    @RequestMapping("/delete")
    public ResponseEntity<List<String>> delete(@RequestParam(value = "id") String id) {
        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/genre")
    public ResponseEntity<List<BookDto>> findByGenre(@RequestParam(value = "genre") String genre) {
        try {
            List<Book> list = bookRepository.findByGenre(genre);
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/name")
    public ResponseEntity<List<BookDto>> findByName(@RequestParam(value = "name") String name) {
        try {
            List<Book> list = bookRepository.findByName(name);
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/author")
    public ResponseEntity<List<BookDto>> findByAuthors(@RequestParam(value = "author") String author) {
        try {
            List<Book> list = bookRepository.findByAuthors(author);
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/findall")
    public ResponseEntity<List<BookDto>> findAll() {
        try {
            List<Book> list = (List<Book>) bookRepository.findAll();
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/comments")
    public ResponseEntity<List<String>> getComments(@RequestParam(value = "id") String id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
            List<String> comments = book.getComments();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/addcomment")
    public ResponseEntity<List<String>> addComment(@RequestParam(value = "id") String id,
            @RequestParam(value = "comment") String comment) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
            List<String> comments = book.getComments();
            comments.add(comment);
            bookRepository.save(book);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
}
