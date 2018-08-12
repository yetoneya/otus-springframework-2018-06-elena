package ru.otus.elena.bookcatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ConfigurationProperties("application")
@SpringBootApplication
@EnableMongoRepositories
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
        

    }
}
