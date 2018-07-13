
package ru.otus.elena.studentiqtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.elena.studentiqtest.acquaintance.Acquaintance;
import ru.otus.elena.studentiqtest.acquaintance.AcquaintanceImpl;

@Configuration
public class AcquaintanceConfig {
    
    @Bean
    public Acquaintance acquaintance(MessageSource messageSource, @Value("${app.lang}")String language){
        return new AcquaintanceImpl(messageSource,language);
    }
}
