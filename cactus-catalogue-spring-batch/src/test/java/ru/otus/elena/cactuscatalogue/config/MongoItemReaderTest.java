package ru.otus.elena.cactuscatalogue.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
public class MongoItemReaderTest {

    @Mock
    private MongoOperations template;

    private Map<String, Sort.Direction> sortOptions;

    private ArgumentCaptor<Query> queryContainer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.sortOptions = new HashMap<>();
        this.sortOptions.put("cactusname", Sort.Direction.DESC);
        this.queryContainer = ArgumentCaptor.forClass(Query.class);
    }

    private MongoItemReaderBuilder<String> getBasicBuilder() {
        return new MongoItemReaderBuilder<String>().template(this.template)
                .targetType(String.class)
                .jsonQuery("{ }")
                .sorts(this.sortOptions)
                .name("mongoReaderTest")
                .pageSize(50);
    }

    private void validateExceptionMessage(MongoItemReaderBuilder<String> builder, String message) {
        try {
            builder.build();
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException iae) {
            assertEquals("IllegalArgumentException message did not match the expected result.", message,
                    iae.getMessage());
        } catch (IllegalStateException ise) {
            assertEquals("IllegalStateException message did not match the expected result.", message,
                    ise.getMessage());
        }
    }

    @Test
    public void testNullTemplate() {
        validateExceptionMessage(new MongoItemReaderBuilder<String>().targetType(String.class)
                .jsonQuery("{ }")
                .sorts(this.sortOptions)
                .name("mongoReaderTest")
                .pageSize(50), "template is required.");
    }

    @Test
    public void testNullTargetType() {
        validateExceptionMessage(new MongoItemReaderBuilder<String>().template(this.template)
                .jsonQuery("{ }")
                .sorts(this.sortOptions)
                .name("mongoReaderTest")
                .pageSize(50), "targetType is required.");
    }

}
