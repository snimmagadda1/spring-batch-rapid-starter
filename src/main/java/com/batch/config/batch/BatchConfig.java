package com.batch.config.batch;

import com.batch.config.readers.ReadersConfig;
import com.batch.config.writers.WritersConfig;
import com.batch.model.Person;
import com.batch.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author snimmagadda1 Configuration class for batch jobs */
@Configuration
@EnableBatchProcessing
@EnableTask
public class BatchConfig {

    @Autowired public JobBuilderFactory jobBuilderFactory;

    @Autowired public StepBuilderFactory stepBuilderFactory;

    /** Holds all readers available for job */
    @Autowired private ReadersConfig readers;

    /** Holds all writers available for job */
    @Autowired private WritersConfig writers;

    /**
     * Test
     *
     * @return PersonItemProcessor Logic for job
     */
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    /**
     * Job pipeline
     *
     * @return Job - configured job
     */
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
    /**
     * Job Step
     *
     * @return Step current configured step
     */
    @Bean
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .<Person, Person>chunk(10)
                .reader(readers.readerCsv())
                .processor(processor())
                .writer(writers.csvItemWriter())
                .build();
    }
}
