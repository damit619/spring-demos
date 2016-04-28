/*
 * Company Header.
 */
package org.javatigers.security.dao.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring integration with JPA and Hibernate as implementer using springs java based configuration. 
 * 
 * @author Amit Dhiman
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(value = { "org.javatigers.security.dao" })
@PropertySource(value = { "classpath:/META-INF/db/jdbc-hibernate.properties" }, ignoreResourceNotFound = true)
public class AppDBConfig {
	
	private static final String USERS_DB_SCRIPT = "/ddl/local/app-spring-sec-insert.sql";
	private static final String OAUTH2_CLIENT_DETAILS_DB_SCRIPT = "/ddl/local/app-spring-sec-oauth-insert.sql";
	private static final String CLOSE = "close";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String HIBERNATE_EJB_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String HIBERNATE_SHOW_GENERATED_DDL= "hibernate.show.generated.ddl";
	
	/**
	 * Boolean value to decide whether initialize DB with seed values or not.
	 */
	@Value(value = "${initialize.database:false}")
	private String initializeDatabase;
	
	/**
	 * Database driver for connection.
	 */
	@Value (value = "${database.driver}")
	private String databaseDriver;
	
	/**
	 * Database URL for connection.
	 */
	@Value (value = "${database.url}")
	private String databaseURL;
	
	/**
	 * Database username for connection.
	 */
	@Value (value = "${database.username}")
	private String databaseUsername;
	
	/**
	 * Database password for connection.
	 */
	@Value (value = "${database.password}")
	private String databasePassword;
	
	/**
	 * JPA property describes that whether to print JPA query on console or not. If this property is true logs will be printed on console.
	 */
	@Value (value = "${hibernate.show.sql}")
	private String hibernateShowSQL;
	
	/**
	 * Environment has access to all environmental variable and properties
	 * values.
	 */
	@Autowired
	private Environment environment;
	
	/**
	 * Data source for connection with database.
	 * 
	 * @return {@link DataSource} - for DB connection.
	 */
	@Bean(destroyMethod=CLOSE)
	public DataSource dataSource () {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(databaseDriver);
		dataSource.setUrl(databaseURL);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		return dataSource;
	}
	
	/**
	 * JPA adapter for hibernate.
	 * 
	 * @return {@link HibernateJpaVendorAdapter} - return vendor adapter for JPA.
	 */
	@Bean
	public HibernateJpaVendorAdapter hibernateJpaVendorAdaptor () {
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setGenerateDdl(Boolean.parseBoolean(environment.getProperty(HIBERNATE_SHOW_GENERATED_DDL)));
		adapter.setShowSql(Boolean.parseBoolean(environment.getProperty(HIBERNATE_SHOW_SQL)));
		
		return adapter;
	}
	
	/**
	 * Use dataSource and hibernateJpaVendorAdapter to create entityManagerFactory.
	 * 
	 * @param dataSource
	 * @param hibernateJpaVendorAdapter
	 * @return {@link LocalContainerEntityManagerFactoryBean} - cunstructed entityManagerFactory.
	 */
	@Autowired
	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean (DataSource dataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		//TODO verify if this needed.
		//entityManagerFactoryBean.setPackagesToScan(JPA_ENTITIES);
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		entityManagerFactoryBean.afterPropertiesSet();
		
		return entityManagerFactoryBean;
	}
	
	/**
	 * Create and return {@link JpaTransactionManager} for transactions.
	 * 
	 * @param entityManagerFactoryBean
	 * @return {@link JpaTransactionManager} - transaction manager to use while database oprations.
	 */
	@Autowired
	@Bean
	public JpaTransactionManager transactionManager(
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
	
	/**
	 * Use {@link HibernateExceptionTranslator} for exceptions.
	 * 
	 * @return {@link HibernateExceptionTranslator}.
	 */
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
	
	/**
	 * Hibernate properties to use.
	 * 
	 * @return {@link Properties} - to use.
	 */
	private Properties jpaProperties() {
		Properties props = new Properties();
		props.setProperty(HIBERNATE_DIALECT, environment.getProperty(HIBERNATE_DIALECT));
		props.setProperty(HIBERNATE_FORMAT_SQL, environment.getProperty(HIBERNATE_FORMAT_SQL));
		props.setProperty(HIBERNATE_HBM2DDL_AUTO, environment.getProperty(HIBERNATE_HBM2DDL_AUTO));
		props.setProperty(HIBERNATE_EJB_NAMING_STRATEGY, environment.getProperty(HIBERNATE_EJB_NAMING_STRATEGY));
		props.setProperty(HIBERNATE_SHOW_SQL, environment.getProperty(HIBERNATE_SHOW_SQL));
		// add more
		return props;
	}
	
	/**
	 * Populate database with seed values. Take dataSource to establish
	 * connection with database
	 * 
	 * @param dataSource
	 * @return 
	 *         @link(org.springframework.jdbc.datasource.init.DataSourceInitializer
	 *         )
	 */
	@Autowired
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource(USERS_DB_SCRIPT));
		databasePopulator.addScript(new ClassPathResource(OAUTH2_CLIENT_DETAILS_DB_SCRIPT));
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(Boolean.parseBoolean(initializeDatabase));
		return dataSourceInitializer;
	}
	
}
