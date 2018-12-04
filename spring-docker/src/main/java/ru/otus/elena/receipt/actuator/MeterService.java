package ru.otus.elena.receipt.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MeterService {

    @Bean
    public SimpleMeterRegistry simpleMeterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public Counter loginCounter() {
        return simpleMeterRegistry().counter("loginCounter");
    }

    @Bean
    public Counter signupCounter() {
        return simpleMeterRegistry().counter("signupCounter");
    }

    @Bean
    public Counter errorCounter() {
        return simpleMeterRegistry().counter("errorCounter");
    }

    @Bean
    public Counter warnCounter() {
        return simpleMeterRegistry().counter("warnCounter");
    }
    
    @Bean
    public Timer findTimer(){
        return simpleMeterRegistry().timer("findTimer");
    }

}
