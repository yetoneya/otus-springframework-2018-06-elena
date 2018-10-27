package ru.otus.elena.bookcatalogue.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.elena.bookcatalogue.dao.RoleRepository;
import ru.otus.elena.bookcatalogue.dao.UserRepository;
import ru.otus.elena.bookcatalogue.domain.Role;
import ru.otus.elena.bookcatalogue.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ConfigurationProperties("application")
@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserControllerRestTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    User user;

    @Before
    public void setUp() {
        userRepository.deleteAll();
        Role roleUser = roleRepository.findByRole("USER");
        user = new User("e", LocalDate.of(1971, Month.MARCH, 01), "e", new HashSet<Role>() {
            {
                add(roleUser);
            }
        });
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteUserTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/user/delete").param("id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void refreshUserTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/user/refresh").param("id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }
}
