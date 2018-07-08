
package ru.otus.elena.studentiqtest.iqtest;

import java.util.Scanner;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IQTestImplTest {
    
    public IQTestImplTest() {
    }

    @Test
    public void testIQtest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        IQTestImpl iqt=context.getBean( IQTestImpl.class);
        Scanner sc=new Scanner(System.in);
        System.out.println(">");
        sc.nextLine();
        System.out.println(iqt.IQtest());
        
    }
    
}
