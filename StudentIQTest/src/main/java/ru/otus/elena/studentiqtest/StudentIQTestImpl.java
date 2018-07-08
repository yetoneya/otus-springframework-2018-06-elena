
package ru.otus.elena.studentiqtest;

import ru.otus.elena.studentiqtest.acquaintance.Acquaintance;
import ru.otus.elena.studentiqtest.person.Person;
import ru.otus.elena.studentiqtest.iqtest.IQTest;
import ru.otus.elena.studentiqtest.testresult.TestResult;

public class StudentIQTestImpl implements StudentIQTest{
    
    private final Acquaintance acquaintance;
    private final IQTest iQTest;
    private final TestResult testResult;
    
    public StudentIQTestImpl(Acquaintance acquaintance,IQTest iQTest,TestResult testResult){
        this.acquaintance=acquaintance;
        this.iQTest=iQTest;
        this.testResult=testResult;
    }
   
    @Override
    public void start(){
        System.out.println("start test");
        Person student=acquaintance.becomeAcquainted();
        int result=iQTest.IQtest();
        testResult.printTestResult(student,result);
        
    }
}
