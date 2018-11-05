package ru.otus.elena.cactuscatalogue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.elena.cactuscatalogue.domain.Cactus;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.data.domain.Sort;


@EnableBatchProcessing
@Configuration
public class BatchConfig {
 
    private final Logger logger = LoggerFactory.getLogger("Batch");
 
    public JobBuilderFactory jobBuilderFactory;

    public StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;
    
    private final MongoTemplate mongoTemplate;
    
    @Autowired
    public BatchConfig(
            StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, DataSource dataSource, MongoTemplate mongoTemplate) {
        
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.mongoTemplate = mongoTemplate;
    }


    @Bean
    public MongoItemReader<Cactus> mongoItemReader() throws Exception {
        Map<String, Sort.Direction>sortOptions = new HashMap<>();
        sortOptions.put("cactusname", Sort.Direction.DESC);
        return new MongoItemReaderBuilder<Cactus>()
                .name("cactusItemReader")
                .template(mongoTemplate)
                .collection("cactus")
                .targetType(Cactus.class)                              
                .jsonQuery("{}")
                .sorts(sortOptions)
                .build();
    }

    @Bean
    public ItemProcessor processor() {
        return new ItemProcessor<Cactus, Cactus>() {
            @Override
            public Cactus process(Cactus person) throws Exception {
                return person;
            }
        };
    }

    @Bean
    public JdbcBatchItemWriter<Cactus> writer() {
        ItemPreparedStatementSetter<Cactus> cactusSetter
                = new CactusPreparedStatementSetter();
        return new JdbcBatchItemWriterBuilder<Cactus>()
                .dataSource(dataSource)
                .sql("insert into cactus(id, cactusname, size)values(?, ?, ?)")
                .itemPreparedStatementSetter(cactusSetter)
                .build();
    }

    final class CactusPreparedStatementSetter implements ItemPreparedStatementSetter<Cactus> {

        @Override
        public void setValues(Cactus cactus,
                PreparedStatement preparedStatement) throws SQLException {
            preparedStatement.setLong(1, cactus.getId());
            preparedStatement.setString(2, cactus.getCactusname());
            preparedStatement.setLong(3, cactus.getSize());
        }
    }

    @Bean
    public Job migrateJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter writer) throws Exception {
        return stepBuilderFactory.get("step1")
                .chunk(5)
                .reader(mongoItemReader())
                .processor(processor())
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(Object o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(Object o, Object o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(Object o, Exception e) {
                        logger.info("Ошбка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }
}
