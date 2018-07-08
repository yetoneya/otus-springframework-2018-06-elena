/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.elena.studentiqtest.acquaintance;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.elena.studentiqtest.person.Person;

public class AcquaintanceImplTest {
    
    public AcquaintanceImplTest() {
    }

    @Test
    public void testBecomeAcquainted() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        Acquaintance acquaintance=context.getBean(AcquaintanceImpl.class);
        Person person=acquaintance.becomeAcquainted();
        System.out.println(person);
    }
    
}
