package ru.otus.elena.receipt.dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.receipt.domain.Role;

@RunWith(SpringRunner.class)
@ConfigurationProperties("application")
@EnableMongoRepositories
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class RoleRepositoryTest {

    public RoleRepositoryTest() {
    }
   
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleRepository.deleteAll();
    }

    @Test
    public void testFindByRole() {
        System.out.println("findByRole");
        Role role = new Role();
        role.setRole("TEST");
        roleRepository.insert(role);
        Role rerole = roleRepository.findByRole("TEST");
        assertEquals(role, rerole);

    }

}
