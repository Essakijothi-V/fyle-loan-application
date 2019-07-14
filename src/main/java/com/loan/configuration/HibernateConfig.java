package com.loan.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * @author essakijothi_v
 *
 */
@Configuration
public class HibernateConfig {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private Environment environment;
	
	@Bean
    public DataSource dataSource() {
		logger.debug("Creating datasource bean :: {}", environment.getProperty("spring.datasource.url"));
		return DataSourceBuilder.create()
				.driverClassName(environment.getProperty("spring.datasource.driverClassName"))
				.url(environment.getProperty("spring.datasource.url"))
				.username(environment.getProperty("spring.datasource.username"))
				.password(environment.getProperty("spring.datasource.password"))
				.build();
    }
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		logger.debug("Creating sessionFactory bean");
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.loan.entity");
		logger.info("Setting hibernate properties");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
		hibernateProperties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
		hibernateProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
		sessionFactory.setHibernateProperties(hibernateProperties);
		logger.debug("sessionFactory bean created");
		return sessionFactory;
	}
	
	@Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }
}
