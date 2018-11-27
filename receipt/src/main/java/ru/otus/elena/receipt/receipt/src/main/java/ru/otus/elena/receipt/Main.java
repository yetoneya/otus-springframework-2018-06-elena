package ru.otus.elena.receipt;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.otus.elena.receipt.dao.ReceiptRepository;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.dao.UserRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;
import ru.otus.elena.receipt.service.UserService;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.elena.receipt.actuator.HealthCheck;

@ConfigurationProperties("application")
@SpringBootApplication
@EnableMongoRepositories
@EnableWebSecurity
@EnableHystrix
@EnableCircuitBreaker
public class Main {

    Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HealthCheck healthCheck;
    @Value("${lang}")
    private String lang;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);

    }

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @PostConstruct
    public void init() {
        if (lang.equalsIgnoreCase("en")) {
            Locale.setDefault(Locale.ENGLISH);
        } else {
            Locale.setDefault(new Locale("ru", "RU"));
        }
        try {
            //receiptRepository.deleteAll();
            //roleRepository.deleteAll();
            //userRepository.deleteAll();

            Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRole("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole("ROLE_USER");
                roleRepository.save(userRole);
            }

            User admin = userService.findByUsername("admin");
            if (admin == null) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                roles.add(userRole);
                userService.saveUser("admin", "password", roles);
            }

            logger.info(messageSource.getMessage("initialization.success", new String[]{}, Locale.getDefault()));
        } catch (Exception e) {
            logger.error(messageSource.getMessage("initialization.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            healthCheck.health();
        }
    }

}
