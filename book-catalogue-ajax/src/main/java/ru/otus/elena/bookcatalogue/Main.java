package ru.otus.elena.bookcatalogue;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.elena.bookcatalogue.dao.BookRepository;
import ru.otus.elena.bookcatalogue.domain.Book;

@SpringBootApplication
public class Main {
    
    @Autowired
    private BookRepository repository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
        //Console.main(args);

    }
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @PostConstruct
    public void init() {
        Book book1 = new Book("interactive three pigs", "fantasy", "volf");
        book1.addComment("very yammy");
        book1.addComment("very nice");
        repository.save(book1);
        Book book2 = new Book("interactive kolobok", "fantasy", "hare, volf, bear"); 
        book2.addComment("too dry");
        book2.addComment("too dirty");
        repository.save(book2);
    }
}
