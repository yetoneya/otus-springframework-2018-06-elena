
package ru.otus.elena.studentiqtest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.elena.studentiqtest.studentiqtest.StudentIQTest;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {
      
    private final static String PROPERTIES="src/main/resources/application.properties";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Random generator = new Random();
        int lang = generator.nextInt(2);
        FileInputStream in =new FileInputStream(PROPERTIES);
        Properties props=new Properties();
        props.load(in);
        in.close();
        if(lang==0){
            props.setProperty("app.lang", "en");
        }
        else{
            props.setProperty("app.lang","ru");
        }
        FileOutputStream out=new FileOutputStream(PROPERTIES);
        props.store(out, null);
        out.close();
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
