package by.hrychanok.training.shop.web.app;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("by.hrychanok.training.shop")
public class SpringConfigForWeb {
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "org.postgresql.Driver";
	private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:postgresql://localhost:5432/dbfortest";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "postgres";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "postgres";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "true";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "by.hrychanok.training.shop.model";
	  private static final String PROP_HIBERNATE_enable_lazy_load_no_trans = "true";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
		entityManagerFactoryBean.setJpaProperties(hibernateProp());
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
		dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
		dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
		dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);
		return dataSource;
	}

	private Properties hibernateProp() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", PROPERTY_NAME_HIBERNATE_DIALECT);
		properties.put("hibernate.show_sql", PROPERTY_NAME_HIBERNATE_SHOW_SQL);
		properties.put("hibernate.enable_lazy_load_no_trans", PROP_HIBERNATE_enable_lazy_load_no_trans);
		properties.put("hibernate.physical_naming_strategy", "by.hrychanok.training.shop.model.util.PhysicalNamingStrategyImpl");
		return properties;
	}

}
