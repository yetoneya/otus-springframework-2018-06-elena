
package ru.otus.elena.studentiqtest.acquaintance;

import java.util.Scanner;
import org.springframework.stereotype.Service;
import ru.otus.elena.studentiqtest.person.Person;

@Service
public class AcquaintanceImpl implements Acquaintance{
         
    @Override
    public Person becomeAcquainted(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What is your name?>");
            String studentName = scanner.nextLine();
            if (studentName.length() == 0) {
                studentName = "Student";
            }
            System.out.println("Hello, " + studentName + ", you have to answer five questions:");

            return new Person(studentName);
        } catch (Exception ex) {
            return null;
        }
    }

}
