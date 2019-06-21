package org.jeavio.meetin.backend.service;

import org.springframework.http.ResponseEntity;

public interface RoomService {

	ResponseEntity<?> fetchRooms();

}