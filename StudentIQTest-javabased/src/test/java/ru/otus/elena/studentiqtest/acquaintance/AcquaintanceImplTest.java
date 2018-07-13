
package ru.otus.elena.studentiqtest.acquaintance;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.elena.studentiqtest.Main;
import ru.otus.elena.studentiqtest.person.Person;

public class AcquaintanceImplTest {

    public AcquaintanceImplTest() {
    }
    
    @Test
    public void testBecomeAcquainted() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Acquaintance acquaintance=context.getBean(Acquaintance.class);        
        Person person = acquaintance.becomeAcquainted();
        System.out.println("name=" + person.getPersonName());
        context.close();
    }

    
}
