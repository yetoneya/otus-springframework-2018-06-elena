package ru.otus.elena.bookcatalogue.dao;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.bookcatalogue.domain.Role;
import ru.otus.elena.bookcatalogue.domain.User;

@RunWith(SpringRunner.class)
@ConfigurationProperties("application")
@EnableMongoRepositories
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testFindByUsername() {
        System.out.println("findByUsername");
        Role role= new Role();
        role.setRole("TEST");
        roleRepository.save(role);
        Set<Role>roles=new HashSet<Role>(){{add(role);}};
        User user = new User();
        user.setUsername("piotr");
        user.setPassword(new BCryptPasswordEncoder().encode("piotr"));
        user.setBirthday(LocalDate.now());
        user.setRoles(roles);
        userRepository.insert(user);
        User reuser = userRepository.findByUsername("piotr");
        assertEquals(user, reuser);
    }

}
