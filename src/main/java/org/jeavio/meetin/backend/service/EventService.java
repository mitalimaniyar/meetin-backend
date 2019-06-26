package org.jeavio.meetin.backend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.model.Event;

public interface EventService {

	boolean addEvent(EventDTO newEvent, String empId);

	boolean checkSlotAvailability(String roomName, Date start, Date end);

	boolean checkSlotAvailability(String roomName, String start, String end);

	boolean existsByEventId(String eventId);

	List<EventDetails> findEventByRoomName(String string);
	
	List<EventDetails> findEventByEmpId(String empId);

	List<Event> getAllBookings();

	Map<String,List<EventDetails>> getAllEventGroupByRoomName();

	List<EventDetails> getPastEvents(String empId);

	public List<EventDetails> getFutureEvents(String empId);

	void cancelEvent(String eventId);
	
	Event findByEventId(String eventId);
}
