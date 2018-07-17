
package ru.otus.elena.studentiqtest.testresult;

import ru.otus.elena.studentiqtest.person.Person;

public interface TestResult {

    public void printResult(Person person, int result);
    public void setLanguage(String language);
}
