package com.snimma1.config.batch;

import com.snimma1.config.readers.ReadersConfig;
import com.snimma1.config.writers.WritersConfing;
import com.snimma1.model.Person;
import com.snimma1.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 
 * @author snimmagadda1
 * Configuration class for batch jobs and default datasource
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    /**
     * Holds all readers available for job
     */
    @Autowired
    private ReadersConfig readers;

    /**
    * Holds all writers available for job
    */
    @Autowired
    private WritersConfing writers;

    /**
     * Datasource to write job metadata
     */
    @Resource(name="dsTest")
    private DataSource dsTest;
    
    /**
     * Configure default batch datasource
     * @param dataSource
     * @return
     */
    @Bean
    BatchConfigurer batchConfigurer(DataSource dataSource){
      return new DefaultBatchConfigurer(dsTest);
    }

    /**
     * Configure default task datasource
     * @param dataSource
     * @return
     */
    @Bean
    TaskConfigurer taskConfigurer(DataSource dataSource){
        return new DefaultTaskConfigurer(dsTest);
    }

    /**
     * Test
     * @return PersonItemProcessor Logic for job
     */
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

   /**
    * Job pipeline
    * @return Job - configured job
    */
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
    /**
     * Job Step
     * @return Step current configured step
     */
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader(readers.readerCsv())
                .processor(processor())
                .writer(writers.csvItemWriter())
                .build();
    }
   
}
