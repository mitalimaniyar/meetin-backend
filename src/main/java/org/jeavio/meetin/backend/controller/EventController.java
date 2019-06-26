package org.jeavio.meetin.backend.controller;

import java.util.Map;

import javax.validation.Valid;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.security.AppUser;
import org.jeavio.meetin.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class EventController {

	@Autowired
	EventService eventService;
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/events")
	public ResponseEntity<?> getBookings(){
		ResponseEntity<?> response = null;
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/events/{roomName}")
	public ResponseEntity<?> getRoomBookings(){
		ResponseEntity<?> response = null;
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/events/my/past")
	public ResponseEntity<?> pastEvents(){
		ResponseEntity<?> response = null;
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(401,"Authorization Required."));
			return response;
		}
		String empId = ((AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
////		 = eventService.getPastEvents(empId);
//		if(status) {
//			response = ResponseEntity.status(HttpStatus.OK).
//		}else {
//			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404,"User Not Found"));
//		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/events/my/future")
	public ResponseEntity<?> futureEvents(){
		ResponseEntity<?> response = null;
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(401,"Authorization Required."));
			return response;
		}
		String empId = ((AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
//		boolean status = eventService.getFutureEvents(empId);
//		if(status) {
//			
//		}else {
//			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404,"User Not Found"));
//		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST,path = "/api/events")
	public ResponseEntity<?> addEvent(@Valid @RequestBody EventDTO newEvent){
		ResponseEntity<?> response = null;
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(401,"Authorization Required."));
			return response;
		}
		String empId = ((AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		
		if(eventService.checkSlotAvailability(newEvent.getRoomName(),newEvent.getStart(), newEvent.getEnd())) {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(409,"Unable to add event. Requested time slot is conflicting."));
			return response;
		}
		boolean status = eventService.addEvent(newEvent,empId);
		if(status) {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Success"));
		}
		else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Unable to add event"));
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.DELETE,path = "/api/events")
	public ResponseEntity<?> cancelEvent(@RequestBody Map<String,String> body){
		ResponseEntity<?> response = null;
		String eventId=body.get("eventId");
		if(!eventService.existsByEventId(eventId))
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404,"Event not found."));
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT,path = "/api/events")
	public ResponseEntity<?> modifyEventDetails(@Valid @RequestBody EventDTO modifiedEvent){
		ResponseEntity<?> response = null;
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT,path = "/api/events/removemember")
	public ResponseEntity<?> removeMemberFromEvent(@RequestBody Map<String,Integer> body){
		ResponseEntity<?> response = null;
		return response;
	}
}
