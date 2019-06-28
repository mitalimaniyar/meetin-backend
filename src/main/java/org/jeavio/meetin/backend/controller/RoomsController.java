package org.jeavio.meetin.backend.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.RoomDetails;
import org.jeavio.meetin.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomsController {

	@Autowired
	RoomService roomService;

	@RequestMapping(method = RequestMethod.GET, path = "/api/rooms")
	public ResponseEntity<?> getAllRooms() {
		List<RoomDetails> rooms = roomService.fetchRooms();
		ResponseEntity<?> response = null;
		if ( rooms == null || rooms.isEmpty())
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ApiResponse(503, "Service Unavailable. No Rooms Found for Selection."));
		else
			response = ResponseEntity.status(HttpStatus.OK).body(rooms);

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/api/rooms")
	public ResponseEntity<?> addRoom(@Valid @RequestBody RoomDetails roomDetails) {
		ResponseEntity<?> response = null;
		String roomName = roomDetails.getName();
		if (!roomService.existsByRoomName(roomName)) {
			roomService.addRoom(roomDetails);
			response = ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Room Created."));
		} else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Room already exist with same name."));
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/api/rooms/")
	public ResponseEntity<?> removeRoom(@RequestBody Map<String, String> body) {
		ResponseEntity<?> response = null;
		String roomName = body.get("roomName");
		if (roomService.existsByRoomName(roomName)) {
			roomService.removeRoom(roomName);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Room Removed."));
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(404, "Room Not found."));
		}
		return response;

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/api/rooms")
	public ResponseEntity<?> modifyRoom(@Valid @RequestBody RoomDetails  modifiedRoomDetails) {
		ResponseEntity<?> response = null;
		Integer roomId =  modifiedRoomDetails.getRoomId();
		String newRoomName =  modifiedRoomDetails.getName();

		if (!roomService.existsByRoomId(roomId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(404, "Requested room not found."));
		} else if (roomService.existsByRoomName(newRoomName)) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Room name already exists."));
		} else {
			roomService.modifyRoom(modifiedRoomDetails);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Room Details Modified."));
		}
		return response;
	}
}
