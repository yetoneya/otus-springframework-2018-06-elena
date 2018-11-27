package ru.otus.elena.receipt.service;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
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
import ru.otus.elena.receipt.dao.ReceiptRepository;
import ru.otus.elena.receipt.domain.Receipt;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ConfigurationProperties("application")
@EnableMongoRepositories
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReceiptServiceTest {

    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private ReceiptRepository receiptRepository;

    @Before
    public void setUp() {
        receiptRepository.deleteAll();
    }

    @Test
    public void findReceiptTest() {
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Map<String, String> map = new HashMap<>();
        map.put("type", "receipt");
        map.put("name", "receipt");
        map.put("componemt", "receipt");
        Receipt re = receiptService.findReceipt(map).get(0);
        assertEquals(receipt, re);
        map.clear();
        map.put("type", "");
        map.put("name", "");
        map.put("componemt", "");
        re = receiptService.findReceipt(map).get(0);
        assertEquals(receipt, re);
    }

}
