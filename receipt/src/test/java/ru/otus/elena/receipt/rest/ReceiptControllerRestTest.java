package ru.otus.elena.receipt.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.otus.elena.receipt.dao.ReceiptRepository;
import ru.otus.elena.receipt.domain.Receipt;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ConfigurationProperties("application")
@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReceiptControllerRestTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReceiptRepository receiptRepository;

    @Before
    public void setUp() {
        receiptRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin")
    public void saveReceiptTest() throws Exception {
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/receipt/save/?type=receipt&name=receipt&component=receipt&description=receipt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    @WithMockUser(username = "admin")
    public void deleteTest() throws Exception {
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/receipt/delete").param("id", receipt.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    @WithMockUser(username = "admin")
    public void selectTest() throws Exception {
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/receipt/select").param("id", receipt.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

    @Test
    @WithMockUser(username = "admin")
    public void findReceiptTest() throws Exception {
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        MediaType type = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/receipt/find/?type=receipt&name=receipt&component=receipt")).andExpect(status().isOk())
                .andExpect(content().contentType(type));
    }

}
