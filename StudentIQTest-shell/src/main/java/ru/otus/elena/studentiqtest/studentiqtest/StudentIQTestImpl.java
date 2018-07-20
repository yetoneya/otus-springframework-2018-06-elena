
package ru.otus.elena.studentiqtest.studentiqtest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.elena.studentiqtest.acquaintance.Acquaintance;
import ru.otus.elena.studentiqtest.person.Person;
import ru.otus.elena.studentiqtest.iqtest.IQTest;
import ru.otus.elena.studentiqtest.testresult.TestResult;

@Service
public class StudentIQTestImpl implements StudentIQTest{
    
    private final Acquaintance acquaintance;
    private final IQTest iQTest;
    private final TestResult testResult;
    
    @Autowired
    public StudentIQTestImpl(Acquaintance acquaintance,IQTest iQTest,TestResult testResult){
        this.acquaintance=acquaintance;
        this.iQTest=iQTest;
        this.testResult=testResult;
    }
   
    @Override
    public void start(String language){
       
        setLanguage(language);
        System.out.println("start test");
        Person student = acquaintance.becomeAcquainted();
        int result = iQTest.IQtest();
        testResult.printResult(student, result);
    }

    private void setLanguage(String language) {
         acquaintance.setLanguage(language);
         iQTest.setLanguage(language);
         testResult.setLanguage(language);
    }
}
