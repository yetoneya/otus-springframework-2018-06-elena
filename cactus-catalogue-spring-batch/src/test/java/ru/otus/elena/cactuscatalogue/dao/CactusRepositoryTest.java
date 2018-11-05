package ru.otus.elena.cactuscatalogue.dao;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.elena.cactuscatalogue.domain.Cactus;


@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@ConfigurationProperties("application")
@EnableMongoRepositories
public class CactusRepositoryTest {

    @Autowired
    CactusRepository mCactusRepository;

    

    @Before
    public void setUp() {
        mCactusRepository.deleteAll();
    }

    @Test
    public void testFindByCactusname() {
        System.out.println("findByName");            
        Cactus cactus=mCactusRepository.insert(new Cactus("mycactus",  1024));
        List<Cactus> recactuses = mCactusRepository.findByCactusname("mycactus");
        System.out.println(recactuses.get(0));

    }

}
