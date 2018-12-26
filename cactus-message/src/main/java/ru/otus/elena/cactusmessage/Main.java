package ru.otus.elena.cactusmessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class Main {
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    }

}
