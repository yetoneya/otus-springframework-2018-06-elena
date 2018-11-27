package ru.otus.elena.receipt.service;

import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.dao.UserRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ConfigurationProperties("application")
@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void findByUsernameTest() {
        System.out.println("findByUsernameTest");
        userService.saveUser("user", "password");
        User user = userService.findByUsername("user");
        assertNotEquals(user, null);
    }

    @Test
    public void saveUserOneTest() {
        System.out.println("saveUserOneTest");
        userService.saveUser("user", "password");
        User user = userService.findByUsername("user");
        assertNotEquals(user, null);
    }

    @Test
    public void saveUserTwoTest() {
        Role role = new Role();
        role.setRole("TEST");
        roleRepository.save(role);
        Set<Role> roles = new HashSet<Role>() {
            {
                add(role);
            }
        };
        userService.saveUser("user", "password", roles);
        User reuser = userService.findByUsername("user");
        assertNotEquals(reuser, null);
    }

    @Test
    public void saveAdmin() {
        Role role = new Role();
        role.setRole("TEST");
        roleRepository.save(role);
        Set<Role> roles = new HashSet<Role>() {
            {
                add(role);
            }
        };
        userService.saveAdmin("user", "password");
        User reuser = userService.findByUsername("user");
        assertNotEquals(reuser, null);
    }

    @Test
    public void deleteUserByIdTest() {
        userService.saveUser("user", "password");
        User user = userService.findByUsername("user");
        userService.deleteUserById(user.getId());
        User reuser = userRepository.findById(user.getId()).orElse(null);
        assertEquals(reuser, null);
    }

    @Test
    public void findAllTest() {
        userService.saveUser("user", "password");
        User reuser = userService.findAll().get(0);
        assertNotEquals(reuser, null);
    }
}
