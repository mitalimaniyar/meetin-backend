package org.jeavio.meetin.backend.controller;

import org.jeavio.meetin.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomsController {

	@Autowired
	RoomService roomService;
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/rooms")
	public ResponseEntity<?> getAllRooms(){
		ResponseEntity<?> response=roomService.fetchRooms();
		return response;
	}
	
//	mongo interaction method to get events based on roomId without auth
//	add event with auth
//	modify event with auth
//	remove event with auth
	
	
}
