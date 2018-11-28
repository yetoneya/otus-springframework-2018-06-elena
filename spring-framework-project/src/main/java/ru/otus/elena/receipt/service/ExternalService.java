package ru.otus.elena.receipt.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String FAILURE_FILE_NAME = "image/cucumber.jpg";

    @HystrixCommand(fallbackMethod = "defaultMethod")
    public String getPicture(String url) {
        try {
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
            if (imageBytes.length == 0) {
                throw new IllegalArgumentException();
            }
            return url;
        } catch (Exception e) {
            return FAILURE_FILE_NAME;
        }
    }

    private String defaultMethod(String url) {
        return FAILURE_FILE_NAME;
    }
}
