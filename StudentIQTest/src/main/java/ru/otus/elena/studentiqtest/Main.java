
package ru.otus.elena.studentiqtest;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(StudentIQTestImpl.class).start();
        context.close();

    }
}
