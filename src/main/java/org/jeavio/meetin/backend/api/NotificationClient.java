package org.jeavio.meetin.backend.api;

import java.util.Map;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;

@FeignClient(name = "notification")
public interface NotificationClient {

	@PostMapping("/api/notify")
	@Headers("Content-Type: application/json")
	public ApiResponse sendNotification(@RequestBody Map<String,Object> body);
}
