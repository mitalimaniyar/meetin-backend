package org.jeavio.meetin.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MeetinBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetinBackendApplication.class, args);
	}

}
