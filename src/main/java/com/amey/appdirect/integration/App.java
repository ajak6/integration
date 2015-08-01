package com.amey.appdirect.integration;

import org.genericdao.ConnectionPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author amey
 *
 */
@SpringBootApplication
public class App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public ConnectionPool connection() {
		System.out.println("creating database connectio ");
		return new ConnectionPool("com.mysql.jdbc.Driver",
				"jdbc:mysql://my-east-db-instance.cq6vzlml6j4g.us-east-1.rds.amazonaws.com/testing", "root",
				"12345678");
	}
	
}

/// ameytest-34007
// secret : A7Q12Vao2SdLik8N