package org.jeavio.meetin.backend.api;

import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.EventDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;

@FeignClient(name = "eventManager")
public interface EventManagerClient {

	@PostMapping("/api/events/all")
	@Headers("Content-Type: application/json")
	public Map<String, List<EventDetails>> getEventsByRooms(@RequestBody Map<String, List<String>> body);

	@PostMapping("/api/events/room")
	public List<EventDetails> getEventByRoomName(@RequestBody Map<String, String> body);

	@PostMapping("/api/events/my")
	@Headers("Content-Type: application/json")
	public List<EventDetails> getUserEvents(@RequestBody Map<String, String> body);

	@PostMapping("/api/events/my/past")
	@Headers("Content-Type: application/json")
	public List<EventDetails> getUserPastEvents(@RequestBody Map<String, String> body);

	@PostMapping("/api/events/my/future")
	@Headers("Content-Type: application/json")
	public List<EventDetails> getUserFutureEvents(@RequestBody Map<String, String> body);

	@DeleteMapping("/api/events")
	@Headers("Content-Type: application/json")
	public EventDetails cancelEvent(@RequestBody Map<String, String> body);

	@PostMapping("/api/events")
	@Headers("Content-Type: application/json")
	public boolean addEvent(@RequestBody EventDetails event);

	@PostMapping("/api/events/checkavailability")
	@Headers("Content-Type: application/json")
	public boolean checkSlotAvailability(@RequestBody EventDetails event);

	@GetMapping("/api/events/exists/{eventId}")
	@Headers("Content-Type: application/json")
	public boolean existEvent(@PathVariable(name = "eventId") String eventId);

	@GetMapping("/api/events/info/{eventId}")
	@Headers("Content-Type: application/json")
	public EventDetails getEventById(@PathVariable(name = "eventId") String eventId);

	@PostMapping("/api/events/checkavailability/modify")
	@Headers("Content-Type: application/json")
	public boolean checkModifiedSlotAvailability(@RequestBody EventDetails dummyEvent);

	@PutMapping("/api/events")
	@Headers("Content-Type: application/json")
	public boolean modifyEvent(@RequestBody EventDetails modifiedEvent);
}
