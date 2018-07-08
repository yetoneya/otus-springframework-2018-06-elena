
package ru.otus.elena.studentiqtest.person;

public class Person {
    
    private final String personName;
    
    public Person(String personName){
        this.personName=personName;
    }

    public String getPersonName() {
        return personName;
    }

    @Override
    public String toString() {
        return "name = " +this.getPersonName();
    }
    
    
}
