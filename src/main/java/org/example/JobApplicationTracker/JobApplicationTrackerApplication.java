package org.example.JobApplicationTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class JobApplicationTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobApplicationTrackerApplication.class, args);
		
		System.out.println("HOLA AMIGO!!");
	}

}
