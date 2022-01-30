package com.batch.config.writers;

import com.batch.model.Person;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@PropertySource("classpath:application.yaml")
public class WritersConfig {

  // <The config strings and writers are for demo purposes. Remove for your job

  @Value("${export.file.csv}")
  private String csvFile;

  @Value("${export.file.txt}")
  private String txtFile;

  /** @return ItemWriter */
  @Bean
  public ItemWriter<Person> csvItemWriter() {
    FlatFileItemWriter<Person> csvWriter = new FlatFileItemWriter<>();
    csvWriter.setResource(new FileSystemResource(csvFile));
    csvWriter.setShouldDeleteIfExists(true);

    DelimitedLineAggregator<Person> lineAgg = new DelimitedLineAggregator<>();
    lineAgg.setDelimiter(",");
    BeanWrapperFieldExtractor<Person> extractor = new BeanWrapperFieldExtractor<>();
    extractor.setNames(new String[] {"firstName", "lastName"});
    lineAgg.setFieldExtractor(extractor);
    csvWriter.setLineAggregator(lineAgg);
    return csvWriter;
  }

  /** @return ItemWriter */
  @Bean
  public ItemWriter<Person> txtItemWriter() {
    FlatFileItemWriter<Person> txtWriter = new FlatFileItemWriter<>();
    txtWriter.setResource(new FileSystemResource(txtFile));
    txtWriter.setShouldDeleteIfExists(true);

    DelimitedLineAggregator<Person> lineAgg = new DelimitedLineAggregator<>();
    lineAgg.setDelimiter("##");
    BeanWrapperFieldExtractor<Person> extractor = new BeanWrapperFieldExtractor<>();
    extractor.setNames(new String[] {"firstName", "lastName"});
    lineAgg.setFieldExtractor(extractor);
    txtWriter.setLineAggregator(lineAgg);
    return txtWriter;
  }
}
