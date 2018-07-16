
package ru.otus.elena.studentiqtest;


import ru.otus.elena.studentiqtest.studentiqtest.StudentIQTest;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;



@SpringBootApplication
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ConfigurableApplicationContext context=SpringApplication.run(Main.class, args);        
        context.getBean(StudentIQTest.class).start();
        context.close();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource ms=new ReloadableResourceBundleMessageSource();
        ms.setBasename("/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
