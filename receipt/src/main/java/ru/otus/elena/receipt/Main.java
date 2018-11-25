package ru.otus.elena.receipt;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;
import ru.otus.elena.receipt.service.UserService;

@ConfigurationProperties("application")
@SpringBootApplication
@EnableMongoRepositories
@EnableWebSecurity
public class Main {

    Logger logger = LoggerFactory.getLogger(Main.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);

    }

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @PostConstruct
    public void init() {
        try {
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

            logger.info("initialization was succesful");
        } catch (Exception e) {
            logger.error("initialization wasn't successful");    
        }
    }
}
