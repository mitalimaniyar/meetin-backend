package org.jeavio.meetin.backend;

import org.jeavio.meetin.backend.dao.EventRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {EventRepository.class})
public class MeetinBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetinBackendApplication.class, args);
	}

}
