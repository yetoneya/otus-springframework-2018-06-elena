
package ru.otus.elena.studentiqtest.acquaintance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.studentiqtest.person.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcquaintanceImplTest {
    
    @Autowired
    Acquaintance acquaintance;
    
    public AcquaintanceImplTest() {
    }

    @Test
    public void testBecomeAcquainted() {
        Person person=acquaintance.becomeAcquainted();
        System.out.println("person:"+person);
    }
    
}
