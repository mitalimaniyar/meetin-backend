package org.jeavio.meetin.backend.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.ConflictReportDTO;
import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.security.AppUser;
import org.jeavio.meetin.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	@Autowired
	EventService eventService;

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/events")
	public ResponseEntity<?> getBookings() {
		ResponseEntity<?> response = null;
		Map<String, List<EventDetails>> events = eventService.getAllEventGroupByRoomName();
		response = ResponseEntity.status(HttpStatus.OK).body(events);
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/events/all/{roomId}")
	public ResponseEntity<?> getRoomBookings(@PathVariable(name = "roomId") Integer roomId) {
		ResponseEntity<?> response = null;
		List<EventDetails> events = eventService.findEventByRoomId(roomId);
		response = ResponseEntity.status(HttpStatus.OK).body(events);
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/events/my")
	public ResponseEntity<?> userEvents() {
		ResponseEntity<?> response = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse(401, "Authorization Required."));
			return response;
		}
		String empId = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		List<EventDetails> events = eventService.findEventByEmpId(empId);
		if (!events.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.OK).body(events);
		} else {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(404, "Not Found"));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/events/my/past")
	public ResponseEntity<?> pastEvents() {
		ResponseEntity<?> response = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse(401, "Authorization Required."));
			return response;
		}
		String empId = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		List<EventDetails> pastEvents = eventService.getPastEvents(empId);
		if (!pastEvents.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.OK).body(pastEvents);
		} else {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(404, "Not Found"));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/events/my/future")
	public ResponseEntity<?> futureEvents() {
		ResponseEntity<?> response = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse(401, "Authorization Required."));
			return response;
		}
		String empId = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		List<EventDetails> futureEvents = eventService.getFutureEvents(empId);
		if (!futureEvents.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.OK).body(futureEvents);
		} else {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(404, "Not Found"));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS','CREATE_ACCESS')")
	@RequestMapping(method = RequestMethod.POST, path = "/api/events")
	public ResponseEntity<?> addEvent(@Valid @RequestBody EventDTO newEvent) {
		ResponseEntity<?> response = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse(401, "Authorization Required."));
			return response;
		}
		String empId = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		if (newEvent.getStart() == null || newEvent.getEnd() == null
				|| eventService.checkTime(newEvent.getStart(), newEvent.getEnd())) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Start time is after end time"));
		}

		if (!eventService.checkSlotAvailability(newEvent.getRoomName(), newEvent.getStart(), newEvent.getEnd())) {
			response = ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse(409, "Unable to add event. Requested time slot is conflicting."));
			return response;
		}
		ConflictReportDTO conficts = eventService.addEvent(newEvent, empId);
		if (conficts.getRoomName() == null)
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success"));
		else if (conficts.getRoomName() != null && conficts.getDates().isEmpty())
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Unable to add event"));
		else
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(conficts);
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS_'.concat(#body.get('eventId')!=null?#body.get('eventId'):'none'),'DELETE_ACCESS')")
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/events")
	public ResponseEntity<?> cancelEvent(@RequestBody Map<String, String> body) {
		ResponseEntity<?> response = null;
		String eventId = body.get("eventId");
		if (!eventService.existsById(eventId))
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Event not found."));
		else {
			boolean status = eventService.cancelEvent(eventId);
			if (status)
				response = ResponseEntity.status(HttpStatus.OK).body("Success");
			else
				response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse(500, "Unable to cancel event"));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'EVENTS_'.concat(#modifiedEvent.id!=null?#modifiedEvent.id:'none'),'MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.PUT, path = "/api/events")
	public ResponseEntity<?> modifyEventDetails(@Valid @RequestBody EventDTO modifiedEvent) {
		ResponseEntity<?> response = null;
		if (modifiedEvent.getId() == null || !eventService.existsById(modifiedEvent.getId())) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Event Not found"));
		} else if (modifiedEvent.getStart() == null || modifiedEvent.getEnd() == null
				|| eventService.checkTime(modifiedEvent.getStart(), modifiedEvent.getEnd())) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Start time is after end time"));
		} else if (!eventService.checkSlotAvailability(modifiedEvent)) {
			response = ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse(409, "Unable to update event. Requested time slot is conflicting."));
			return response;
		} else {
			boolean status = eventService.modifyEvent(modifiedEvent);
			if (status)
				response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success"));
			else
				response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse(500, "Unable to modify event"));
		}
		return response;
	}

}
