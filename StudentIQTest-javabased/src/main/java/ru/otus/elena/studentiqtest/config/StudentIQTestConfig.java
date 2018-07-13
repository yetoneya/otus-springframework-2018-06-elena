
package ru.otus.elena.studentiqtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.elena.studentiqtest.acquaintance.Acquaintance;
import ru.otus.elena.studentiqtest.iqtest.IQTest;
import ru.otus.elena.studentiqtest.studentiqtest.StudentIQTest;
import ru.otus.elena.studentiqtest.studentiqtest.StudentIQTestImpl;
import ru.otus.elena.studentiqtest.testresult.TestResult;

@Configuration
public class StudentIQTestConfig {

    @Bean
    public StudentIQTest studentIQTest(Acquaintance acquaintance, IQTest iQTest, TestResult testResult) {
        return new StudentIQTestImpl(acquaintance, iQTest, testResult);
    }
}
