package ru.otus.elena.receipt.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.receipt.domain.Receipt;

@RunWith(SpringRunner.class)
@ConfigurationProperties("application")
@EnableMongoRepositories
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReceiptRepositoryTest {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Before
    public void setUp() {
        receiptRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByType() {
        System.out.println("testFindByType");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByType("receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByType("re").get(0);
        assertEquals(receipt, reReceipt);
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByName() {
        System.out.println("testFindByName");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByName("receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByName("re").get(0);
        assertEquals(receipt, reReceipt);
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByComponent() {
        System.out.println("testFindByComponent");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByComponent("receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByComponent("re").get(0);
        assertEquals(receipt, reReceipt);
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByTypeAndName() {
        System.out.println("testFindByTypeAndName");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByTypeAndName("receipt", "receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByTypeAndName("re", "re").get(0);
        assertEquals(receipt, reReceipt);
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByTypeAndComponent() {
        System.out.println("testFindByTypeAndComponent");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByTypeAndComponent("receipt", "receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByTypeAndComponent("re", "re").get(0);
        assertEquals(receipt, reReceipt);
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByNameAndComponent() {
        System.out.println("testFindByNameAndComponent");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByNameAndComponent("receipt", "receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByNameAndComponent("re", "re").get(0);
        assertEquals(receipt, reReceipt);
    }

    @Test
    @WithMockUser(username = "vasya")
    public void testFindByTypeAndNameAndComponent() {
        System.out.println("testFindByTypeAndNameAndComponent");
        Receipt receipt = new Receipt("receipt", "receipt", "receipt", "receipt", "receipt");
        receiptRepository.save(receipt);
        Receipt reReceipt = receiptRepository.findByTypeAndNameAndComponent("receipt", "receipt", "receipt").get(0);
        assertEquals(receipt, reReceipt);
        reReceipt = receiptRepository.findByTypeAndNameAndComponent("re", "re", "re").get(0);
        assertEquals(receipt, reReceipt);
    }
}
