package ru.otus.elena.studentiqtest.testresult;

import org.springframework.stereotype.Service;
import ru.otus.elena.studentiqtest.person.Person;

@Service
public class TestResultImpl implements TestResult {        

    @Override
    public void printResult(Person person, int result) {
        System.out.println(getMessage(person.getPersonName(),result));
    }

    private String getMessage(String name,int result) {
        String str = null;
        switch (result) {
            case 0:
                str = name + ", I wonder, how have you been managed to get our university??? Your result = " + result;
                break;
            case 1:
                str =name + ", are you sure, you are appropriate to your speciality? Your result = " + result;
                break;
            case 2:
                str = name +", you have to learn hard to be a good student! Your result " + result;
                break;
            case 3:
                str = name + ", you are a good studetnt, but I would like to advise your to prepare better next time. Your result " + result;
                break;
            case 4:
                str = name +", your IQ is high enough! Your result = " + result;
                break;
            case 5:
                str =name + ", you are very clever! Your result = " + result;
                break;
        }
        return str;
    }
}
