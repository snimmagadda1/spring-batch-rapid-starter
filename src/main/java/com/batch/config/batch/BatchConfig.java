package com.batch.config.batch;

import com.batch.config.readers.ReadersConfig;
import com.batch.config.writers.WritersConfig;
import com.batch.model.Person;
import com.batch.processor.PersonItemProcessor;

import org.hibernate.InvalidMappingException;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
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
    public Job coolBatchJob() {
        return jobBuilderFactory
                .get("coolBatchJob")
                .start(stepA())
                .on("FAILED")
                    .to(failure())
                .from(stepA())
                .on("COMPLETED")
                    .to(flow1())
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
                .get("step1Real")
                .<Person, Person>chunk(10)
                .reader(readers.readerCsv())
                .processor(processor())
                .writer(writers.csvItemWriter())
                .build();
    }

    @Bean
    public Step flow1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("Running the success flow!!");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step failure() {
        return stepBuilderFactory.get("flow2").tasklet((contribution, chunkContext) -> {
            System.out.println("Running the failure flow!!");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("stepA").tasklet((contribution, chunkContext) -> {
            System.out.println("Woo");
            return RepeatStatus.FINISHED;
        }).build();
    }
 

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("stepB").tasklet((contribution, chunkContext) -> {
            System.out.println("Hoo!");
            if (true) {
                throw new InvalidMappingException("Failed", "test", "/test");
            }
            return RepeatStatus.FINISHED;
        }).build();
    }
 
}
