package com.batch.config.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class SpringDatasourceConfiguration {

  @Bean(name = "springDataSource")
  @Primary
  public DataSource springDataSource(
      SpringDatasourceConfigurationProperties springDatasourceConfigurationProperties) {
    DriverManagerDataSource datasource = new DriverManagerDataSource();
    datasource.setDriverClassName(springDatasourceConfigurationProperties.getDriverClassName());
    datasource.setUrl(springDatasourceConfigurationProperties.getUrl());
    datasource.setUsername(springDatasourceConfigurationProperties.getUsername());
    datasource.setPassword(springDatasourceConfigurationProperties.getPassword());
    return datasource;
  }

  @Primary
  @Bean(name = "springEntityManager")
  @PersistenceContext(unitName = "springPersU")
  public LocalContainerEntityManagerFactoryBean springEntityManagerFactory(
      @Qualifier("springDataSource") DataSource dataSource) {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setGenerateDdl(true);
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource);
    factory.setPackagesToScan("com.snimma1.model");
    factory.setPersistenceUnitName("springPersU");
    factory.setJpaVendorAdapter(adapter);
    return factory;
  }

  @Primary
  @Bean
  public JpaTransactionManager springJpaTransactionManager(
      @Qualifier("springEntityManager") EntityManagerFactory factory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(factory);
    return transactionManager;
  }
}
