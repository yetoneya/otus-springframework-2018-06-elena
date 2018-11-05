package ru.otus.elena.cactuscatalogue;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ConfigurationProperties("application")
@EnableMongoRepositories
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
        //Console.main(args);

    }
    


    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate((MongoClient) mongo(), "test");
    }


}
