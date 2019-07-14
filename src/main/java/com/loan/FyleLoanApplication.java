package com.loan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author essakijothi_v
 *
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
public class FyleLoanApplication {

	private static final Logger logger = LogManager.getLogger(FyleLoanApplication.class);

	/**
	 * @param args
	 * 
	 * Application execution starts from here.
	 */
	public static void main(String[] args) {
		logger.debug("Application started");
		SpringApplication.run(FyleLoanApplication.class, args);
	}

}
