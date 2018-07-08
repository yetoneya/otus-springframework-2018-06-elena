
package ru.otus.elena.studentiqtest.testresult;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.elena.studentiqtest.person.Person;

public class TestResultImplTest {
    
    public TestResultImplTest() {
    }

    @Test
    public void testPrintTestResult() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestResultImpl testResult=context.getBean(TestResultImpl.class);
        testResult.printTestResult(new Person("vasya"), 1);

    }
    
}
