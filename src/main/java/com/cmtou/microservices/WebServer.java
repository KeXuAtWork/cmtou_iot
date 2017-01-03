package com.cmtou.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * Spring Boot Application for hosting web services
 * 
 * @author Kxu
 */
@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync
@ComponentScan("com.cmtou.microservices" ) 
public class WebServer {



	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		
		System.setProperty("spring.config.name", "web-server");
		
		SpringApplication.run(WebServer.class, args);
	}

	
}
