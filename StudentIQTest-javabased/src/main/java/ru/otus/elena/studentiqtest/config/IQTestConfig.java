
package ru.otus.elena.studentiqtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.elena.studentiqtest.iqtest.IQTest;
import ru.otus.elena.studentiqtest.iqtest.IQTestImpl;

@Configuration
public class IQTestConfig {

    @Bean
    public IQTest iQTest(MessageSource messageSource, @Value("${app.lang}") String language, @Value("${app.enfile}") String enFile, @Value("${app.rufile}") String ruFile) {
        return new IQTestImpl(messageSource, language, enFile, ruFile);
    }
}
