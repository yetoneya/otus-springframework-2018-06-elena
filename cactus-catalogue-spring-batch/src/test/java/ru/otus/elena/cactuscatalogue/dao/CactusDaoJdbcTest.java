package ru.otus.elena.cactuscatalogue.dao;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.cactuscatalogue.domain.Cactus;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
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
    public void testGetAll() {
        List<Cactus> list = cactusDao.getAll();
        assertEquals(list.size(), 0);
    }

    @Test
    public void testGetById() {
        Cactus cactus = cactusDao.getById(1);
        assertEquals(cactus, null);
    }

}
