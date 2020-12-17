package com.snimma1.config.batch;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snimma1.config.readers.ReadersConfig;
import com.snimma1.config.writers.WritersConfing;
import com.snimma1.model.Person;
import com.snimma1.processor.PersonItemProcessor;

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
     * Configure default datasource
     * @param dataSource
     * @return
     */
    @Bean
    BatchConfigurer configurer(DataSource dataSource){
      return new DefaultBatchConfigurer(dsTest);
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
