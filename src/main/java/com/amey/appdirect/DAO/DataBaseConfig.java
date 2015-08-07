package com.amey.appdirect.DAO;

import org.genericdao.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfig {
	
		@Value("${pipes.db.jdbcDriverName}")
		private String jdbcDriverName;
		@Value("${pipes.db.jdbcURL}")
		private String jdbcURL;
		@Value("${pipes.db.user}")
		private String user;
		@Value("${pipes.db.password}")
		private String password;

		@Bean
		//password changed to CMUApp
		public ConnectionPool connection() {
			System.out.println("creating database connectio ");
			return new ConnectionPool(jdbcDriverName, jdbcURL, user, password);
		}
		
	}


//changes for newcommit