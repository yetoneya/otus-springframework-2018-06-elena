
package ru.otus.elena.studentiqtest.acquaintance;

import org.junit.Test;
import ru.otus.elena.studentiqtest.person.Person;

public class AcquaintanceImplTest {
    
    
    
    public AcquaintanceImplTest() {
    }

    @Test
    public void testBecomeAcquainted() {
        Acquaintance acquaintance=new AcquaintanceImpl();
        Person person=acquaintance.becomeAcquainted();
        System.out.println("person:"+person);
    }
    
}
