package ru.otus.elena.bookcatalogue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.elena.bookcatalogue.domain.Book;

@Service
public class ExternalService {

    @HystrixCommand(fallbackMethod = "defaultMethod", commandProperties = {
        @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    public List<Book> getBooks() {
        ParameterizedTypeReference<List<Book>> parameterizedTypeReference = new ParameterizedTypeReference<List<Book>>() {
        };
        ResponseEntity<List<Book>> exchange = new RestTemplate().exchange("http://localhost:9090/newbooks", HttpMethod.GET, null, parameterizedTypeReference);
        List<Book> result = new ArrayList<>();
        boolean added = result.addAll(exchange.getBody());
        System.out.println(result);
        return result;
    }

    private List<Book> defaultMethod() {
        return new ArrayList<>();
    }
}
/**/
