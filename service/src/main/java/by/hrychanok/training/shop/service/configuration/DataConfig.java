package by.hrychanok.training.shop.service.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("by.hrychanok.training.shop")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("by.hrychanok.training.shop.repository")
public class DataConfig {

	private static final String PROP_DATABASE_DRIVER = "db.driver";
	private static final String PROP_DATABASE_PASSWORD = "db.password";
	private static final String PROP_DATABASE_URL = "db.url";
	private static final String PROP_DATABASE_USERNAME = "db.username";
	private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
	private static final String PROP_HIBERNATE_SHOW_SQL = "db.hibernate.show_sql";
	private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "db.entitymanager.packages.to.scan";
	private static final String PROP_HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS = "current_session_context_class";
	private static final String PROP_HIBERNATE_enable_lazy_load_no_trans = "hibernate.enable_lazy_load_no_trans";
	private static final String PROP_HIBERNATE_EJB_NAMING_STRATEGY = "hibernate.physical_naming_strategy";
	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));

		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
		properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
		properties.put(PROP_HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS,
				env.getRequiredProperty(PROP_HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS));
		properties.put(PROP_HIBERNATE_enable_lazy_load_no_trans,
				env.getRequiredProperty(PROP_HIBERNATE_enable_lazy_load_no_trans));
		properties.put(PROP_HIBERNATE_EJB_NAMING_STRATEGY, env.getRequiredProperty(PROP_HIBERNATE_EJB_NAMING_STRATEGY));
		return properties;
	}

}