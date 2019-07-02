//package org.jeavio.meetin.backend.controller;
//
//import java.util.List;
//
//import org.jeavio.meetin.backend.dao.TeamRepository;
//import org.jeavio.meetin.backend.dto.EventDTO;
//import org.jeavio.meetin.backend.dto.EventDetails;
//import org.jeavio.meetin.backend.service.EventService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class demoController {
//
//	@Autowired
//	EventService eventService;
//	
//	@Autowired
//	TeamRepository teamRepository;
//	@PostMapping("/mitali/{teamId}")
//	public boolean send(@PathVariable(name = "teamId") Integer teamId) {
////		eventService.addEvent(newEvent,"MANMI94");
//		return teamRepository.existsById(teamId);
//	}
//}
