package com.snimma1.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * 
 * @author snimmagadda1
 * DB
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DatasourceConfig 
{
	@Value("${db.jdbc.driver}")
	private String driver;
	
	@Value("${db.jdbc.url}")
	private String dbUrl;
	
	@Value("${db.jdbc.user}")
	private String user;
	
	@Value("${db.jdbc.pass}")
	private String pass;
	
	@Value("${db.mem.script}")
	private String scriptInMem;
	
	/**
	 * @return Datasource configured datasource
	 */
	@Bean(name="dsTest")
    @Qualifier("dsTest")
    @Primary
	public DataSource dataSourceTest() 
	{	
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
			.addScript(scriptInMem)
			.build();
		return db;
	}
}
