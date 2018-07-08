
package ru.otus.elena.studentiqtest.acquaintance;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ru.otus.elena.studentiqtest.person.Person;

public class AcquaintanceImpl implements Acquaintance{
     
    
    @Override
    public Person becomeAcquainted(){
        try{
            Scanner scanner=new Scanner(System.in);
            System.out.println("What is your name?>");
            String studentName=scanner.nextLine();          
            System.out.println("Hello, "+studentName+" you have to answer five questions:");
            //scanner.close();
            return new Person(studentName);
        }catch(Exception ex){
            return null;
        }
    }

}
