package org.jeavio.meetin.backend.service;

import java.util.List;

import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.RoomDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRepository;
	
	public ResponseEntity<?> fetchRooms(){
		
		List<RoomDetails> rooms=roomRepository.getRooms();
		
		ResponseEntity<Object> response;
		
		if(rooms.isEmpty())
			response=ResponseEntity.status(503).body(new ApiResponse(503,"Service Unavailable. No Rooms Found for Selection."));
		else
			response= ResponseEntity.status(HttpStatus.OK).body(rooms);

		return response;
	}
}
