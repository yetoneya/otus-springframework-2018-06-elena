package ru.otus.elena.receipt.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
