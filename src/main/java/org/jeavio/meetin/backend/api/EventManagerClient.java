package org.jeavio.meetin.backend.api;

import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.EventDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;

@FeignClient(name = "eventManager")
public interface EventManagerClient {

	@PostMapping("/api/events")
	@Headers("Content-Type: application/json")
	public Map<String,List<EventDetails>> getEventsByRooms(@RequestBody Map<String,List<String>> body);
	
	@GetMapping("/api/events/{roomName}")
	public List<EventDetails> getEventByRoomName(@PathVariable String roomName);
	
	@PostMapping("/api/events/my")
	@Headers("Content-Type: application/json")
	public List<EventDetails> getUserEvents(@RequestBody Map<String,String> body);
	
	@PostMapping("/api/events/my/past")
	@Headers("Content-Type: application/json")
	public List<EventDetails> getUserPastEvents(@RequestBody Map<String,String> body);
	
	@PostMapping("/api/events/my/future")
	@Headers("Content-Type: application/json")
	public List<EventDetails> getUserFutureEvents(@RequestBody Map<String,String> body);
	
	@DeleteMapping("/api/events")
	@Headers("Content-Type: application/json")
	public boolean cancelEvent(@RequestBody Map<String,String> body);
	
	@PostMapping("/api/events")
	@Headers("Content-Type: application/json")
	public boolean addEvent(@RequestBody EventDetails event);
	
	@PostMapping("/api/events/checkavailability")
	@Headers("Content-Type: application/json")
	public boolean checkSlotAvailability(@RequestBody Map<String,String> body);

	@PostMapping("/api/events/exists")
	public boolean existEvent(@RequestBody Map<String,String> body);
}
