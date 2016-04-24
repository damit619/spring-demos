package org.javatigers.social.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Data JPA
 * 
 * @author amit.kumar1
 */

@Configuration
// @EnableJpaRepositories(basePackages = { "org.javatigers.social.repository" })
@ComponentScan(basePackages = { "org.javatigers.social.repository" })
@EnableTransactionManagement
public class PersistenceConfig {

	private static final String JPA_ENTITIES = "org.javatigers.social.domain";

	@Value(value = "${init-db}:false")
	String initDB;

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}

	@Autowired
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource) {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(env
				.getProperty("hibernate.show.generated.ddl")));
		hibernateJpaVendorAdapter.setShowSql(Boolean.parseBoolean(env
				.getProperty("hibernate.show.sql")));

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan(JPA_ENTITIES);
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean;
	}

	@Autowired
	@Bean
	public JpaTransactionManager transactionManager(
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect",
				env.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.format_sql",
				env.getProperty("hibernate.format_sql"));
		properties.setProperty("hibernate.hbm2ddl.auto",
				env.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.ejb.naming_strategy",
				env.getProperty("hibernate.ejb.naming_strategy"));
		properties.setProperty("hibernate.show_sql",
				env.getProperty("hibernate.show_sql"));
		// add more
		return properties;
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
		databasePopulator.addScript(new ClassPathResource(
				"/META-INF/db/script/userconnection.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(Boolean.parseBoolean(initDB));
		return dataSourceInitializer;
	}

}
