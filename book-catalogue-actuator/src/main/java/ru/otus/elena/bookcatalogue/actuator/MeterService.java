package ru.otus.elena.bookcatalogue.actuator;

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
    public Timer findAllTimer(){
        return simpleMeterRegistry().timer("findAll");
    }

}
/*SimpleMeterRegistry registry = new SimpleMeterRegistry();
LongTaskTimer longTaskTimer = LongTaskTimer
  .builder("3rdPartyService")
  .register(registry);
 
long currentTaskId = longTaskTimer.start();
try {
    TimeUnit.SECONDS.sleep(2);
} catch (InterruptedException ignored) { }
long timeElapsed = longTaskTimer.stop(currentTaskId);
  
assertTrue(timeElapsed / (int) 1e9 == 2);

timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
*/