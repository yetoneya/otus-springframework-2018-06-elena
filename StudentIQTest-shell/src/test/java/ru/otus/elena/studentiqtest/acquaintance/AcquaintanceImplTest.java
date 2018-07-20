
package ru.otus.elena.studentiqtest.acquaintance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.studentiqtest.person.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED+"=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED+"=false"})
public class AcquaintanceImplTest {
    
    @Autowired
    Acquaintance acquaintance;
    
    public AcquaintanceImplTest() {
    }

    @Test
    public void testBecomeAcquainted() {
        acquaintance.setLanguage("ru");
        Person person=acquaintance.becomeAcquainted();
        System.out.println("person:"+person);
    }
    
}
