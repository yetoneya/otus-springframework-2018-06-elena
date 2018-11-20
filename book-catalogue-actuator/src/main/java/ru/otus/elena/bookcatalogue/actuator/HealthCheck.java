package ru.otus.elena.bookcatalogue.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@ConditionalOnProperty(value = "health.indicator.enabled")
@Component
public class HealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        return Health.down().build();
    }
}
