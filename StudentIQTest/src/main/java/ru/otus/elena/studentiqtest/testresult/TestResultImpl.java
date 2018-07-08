package ru.otus.elena.studentiqtest.testresult;

import ru.otus.elena.studentiqtest.person.Person;

public class TestResultImpl implements TestResult {

    @Override
    public void printTestResult(Person person, int result) {
        switch (result) {
            case 0:
                System.out.println(person.getPersonName() + ", I wonder, how had you been managed to get our university??? Your result = " + result);
                break;
            case 1:
                System.out.println(person.getPersonName() + ", are you sure, you are appropriate to your speciality? Your result = " + result);
                break;
            case 2:
                System.out.println(person.getPersonName() + ", you have to learn hard to be a good student! Your result " + result);
                break;
            case 3:
                System.out.println(person.getPersonName() + ", you are a good studetnt, but I would like to advise your to prepare better next time. Your result " + result);
                break;
            case 4:
                System.out.println(person.getPersonName() + ", your IQ is high enought! Your result = " + result);
                break;
            case 5:
                System.out.println(person.getPersonName() + ", you are very clever! Your result = " + result);
                break;
        }

        System.out.println();

    }
}
