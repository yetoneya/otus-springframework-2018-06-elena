
package ru.otus.elena.studentiqtest.testresult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED+"=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED+"=false"})
public class TestResultImplTest {
    
    @Autowired
    TestResult testResult;
    
    public TestResultImplTest() {
    }

    @Test
    public void testPrintResult() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        testResult.setLanguage("ru");
        Method methodGetMessage=TestResultImpl.class.getDeclaredMethod("getMessage", String.class, int.class);
        methodGetMessage.setAccessible(true);
        String result=(String) methodGetMessage.invoke(testResult, "vasya", 0);
        String expected="vasya, I wonder, how have you been managed to get our university??? Your result = 0";
        assertEquals(result,expected);

    }
    
}
