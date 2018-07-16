package ru.otus.elena.studentiqtest.testresult;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.elena.studentiqtest.person.Person;


@Service
@ConfigurationProperties("application")
public class TestResultImpl implements TestResult {
    
    private final MessageSource messageSource;    
    private String language;
    
    @Autowired
    public TestResultImpl(MessageSource messageSource) {
        this.messageSource = messageSource;

    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    

    @Override
    public void printResult(Person person, int result) {
          System.out.println(getMessage(person.getPersonName(),result));
    }

    private String getMessage(String name, int result) {
        switch (result) {
            case 0:
                return localeMessage("result.zero", name, result);
            case 1:
                return localeMessage("result.one", name, result);
            case 2:
                return localeMessage("result.two", name, result);
            case 3:
                return localeMessage("result.three", name, result);
            case 4:
                return localeMessage("result.four", name, result);
            case 5:
                return localeMessage("result.five", name, result);
            default:
                return "unknown";
        }
    }

    private String localeMessage(String property, String name, int result) {
        String res = String.valueOf(result);
        if (language.equalsIgnoreCase("ru")) {
            return messageSource.getMessage(property, new String[]{name, res}, new Locale("ru", "RU"));
        } else {
            return messageSource.getMessage(property, new String[]{name, res}, Locale.ENGLISH);
        }

    }

}

