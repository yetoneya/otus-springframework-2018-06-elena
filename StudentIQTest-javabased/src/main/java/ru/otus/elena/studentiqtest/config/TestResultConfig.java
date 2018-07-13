
package ru.otus.elena.studentiqtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.elena.studentiqtest.testresult.TestResult;
import ru.otus.elena.studentiqtest.testresult.TestResultImpl;

@Configuration
public class TestResultConfig {

    @Bean
    public TestResult testResult(MessageSource messageSource, @Value("${app.lang}")String language) {
        return new TestResultImpl(messageSource, language);
    }
}
