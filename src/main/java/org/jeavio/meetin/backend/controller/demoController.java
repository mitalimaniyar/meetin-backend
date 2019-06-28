package org.jeavio.meetin.backend.controller;

import java.util.List;

import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

	@Autowired
	EventService eventService;
	
	@PostMapping("/")
	public List<EventDetails> send(@RequestBody EventDTO newEvent) {
		eventService.addEvent(newEvent,"MANMI94");
		return eventService.findEventByEmpId("MANMI94");
	}
}
