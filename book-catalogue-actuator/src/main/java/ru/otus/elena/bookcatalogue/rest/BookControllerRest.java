package ru.otus.elena.bookcatalogue.rest;

import io.micrometer.core.instrument.Counter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;
import ru.otus.elena.bookcatalogue.domain.BookDto;
import ru.otus.elena.bookcatalogue.service.BookService;

@RestController
public class BookControllerRest {

    Logger logger = LoggerFactory.getLogger(BookControllerRest.class);

    @Autowired
    private Counter warnCounter;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @RequestMapping("/save")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) {
        try {
            ArrayList<String> authorList = new ArrayList<>(Arrays.asList(bookDto.getAuthors().split(",")));
            Book book = new Book(bookDto.getName(), bookDto.getGenre(), authorList, bookDto.isAdult());
            bookRepository.save(book);
            BookDto bookdto = BookDto.toBookDto(book);
            logger.info("saveBook was successful");
            return new ResponseEntity<BookDto>(bookdto, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("saveBook wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<BookDto>(new BookDto("", "", ""), HttpStatus.BAD_REQUEST);
        }
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
            logger.info("editBook was successful");
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.warn("editBook wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new BookDto("", "", ""), HttpStatus.OK);
        }
    }

    @RequestMapping("/byid")
    public ResponseEntity<BookDto> findById(@RequestParam(value = "id") String id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
            BookDto bookDto = BookDto.toBookDto(book);
            logger.info("findById was successful");
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findById wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new BookDto("", "", ""), HttpStatus.OK);
        }
    }

    @RequestMapping("/delete")
    public ResponseEntity<List<String>> delete(@RequestParam(value = "id") String id) {
        try {
            bookRepository.deleteById(id);
            logger.info("delete was successful");
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("delete wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/genre")
    public ResponseEntity<List<BookDto>> findByGenre(@RequestParam(value = "genre") String genre) {
        try {
            List<Book> list = bookRepository.findByGenre(genre);
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            logger.info("findByGenre was successful");
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findByGenre wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/name")
    public ResponseEntity<List<BookDto>> findByName(@RequestParam(value = "name") String name) {
        try {
            List<Book> list = bookRepository.findByName(name);
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            logger.info("findByName wasn't successful");
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findByname wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/author")
    public ResponseEntity<List<BookDto>> findByAuthors(@RequestParam(value = "author") String author) {
        try {
            List<Book> list = bookRepository.findByAuthors(author);
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            logger.info("findByAuthors was successful");
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findByAuthors wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/findall")
    public ResponseEntity<List<BookDto>> findAll() {
        try {
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            boolean isAdult = false;
            for (GrantedAuthority authority : authorities) {
                String au = authority.getAuthority();
                if (authority.getAuthority().equals("ROLE_ADULT")) {
                    isAdult = true;
                    break;
                }
            }
            List<Book> list = null;
            if (isAdult) {
                list = bookService.findForAdult();
            } else {
                list = bookService.findForAll();
            }
            List<BookDto> books = list.stream().map(BookDto::toBookDto).collect(toList());
            Collections.sort(books, Comparator.comparing(b -> b.getName()));
            logger.info("findAll was successful");
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findAll wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<BookDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/comments")
    public ResponseEntity<List<String>> getComments(@RequestParam(value = "id") String id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
            List<String> comments = book.getComments();
            logger.info("getComment was successful");
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.warn("getComment wasn't successful");
            warnCounter.increment();
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
            logger.info("addComment was successful");
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.warn("addComment wasn't successful");
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
}
