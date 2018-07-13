
package ru.otus.elena.studentiqtest.acquaintance;

import java.util.Locale;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import ru.otus.elena.studentiqtest.person.Person;


public class AcquaintanceImpl implements Acquaintance{
        
    final private MessageSource messageSource;    
    final private String language;
    
    @Autowired
    public AcquaintanceImpl(MessageSource messageSource, @Value("${app.lang}")String language) {
        this.messageSource = messageSource;
        this.language = language;
    }
    
    @Override
    public Person becomeAcquainted() {
        Locale locale=getLocale();
        try { 
            printQuestion(locale); 
            Scanner scanner = new Scanner(System.in,"UTF-8");            
            String studentName = scanner.nextLine();
            printHello(studentName,locale);            
            return new Person(studentName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private void printQuestion(Locale locale) {
        System.out.println(
                messageSource.getMessage("acq.name", new String[]{},locale));
    }

    private void printHello(String studentName, Locale locale) {
        System.out.println(messageSource.getMessage("acq.hello", new String[]{studentName},locale));
    }

    private Locale getLocale() {
        if (language.equalsIgnoreCase("en")) {
            return Locale.ENGLISH;
        } else {
            return new Locale("ru", "RU");
        }
    }
}
