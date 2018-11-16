package ru.otus.elena.cactusmessage.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.cactusmessage.domain.Cactus;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class CactusDaoJdbcTest {

    @Autowired
    private CactusDaoJdbc cactusDao;

    public CactusDaoJdbcTest() {
    }

    @Before
    public void setUp() {
        cactusDao.deleteAll();
    }

    @Test
    public void testInsert() throws IOException {
        File file = new File("src/test/resources/images/aprocactus_hybr.jpg");
        BufferedImage image = ImageIO.read(file);
        Cactus cactus = new Cactus("aprocactus_hybr", "src/test/resources/images/aprocactus_hybr.jpg", image);
        long id = cactusDao.insert(cactus);
        Cactus recactus = cactusDao.getById(id);
        assertEquals(cactus, recactus);
    }

    @Test
    public void testGetAll() {

    }

    @Test
    public void testGetById() {

    }

    @Test
    public void testDeleteAll() {
        System.out.println("deleteAll");

    }

}
