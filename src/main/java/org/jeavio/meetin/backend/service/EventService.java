package org.jeavio.meetin.backend.service;

import java.util.Date;
import java.util.List;

import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;

public interface EventService {

	boolean addEvent(EventDTO newEvent, String empId);

	boolean checkSlotAvailability(String roomName, Date start, Date end);

	boolean checkSlotAvailability(String roomName, String start, String end);

	boolean existsByEventId(String eventId);

	List<EventDetails> findEventByRoomName(String string);
	
	List<EventDetails> findEventByEmpId(String empId);
}
