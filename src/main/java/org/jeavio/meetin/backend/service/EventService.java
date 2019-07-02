package org.jeavio.meetin.backend.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;

public interface EventService {

	public boolean addEvent(EventDTO newEvent, String empId);

	public boolean checkSlotAvailability(String roomName, Date start, Date end);

	public boolean checkSlotAvailability(String roomName, String start, String end);

	public List<EventDetails> findEventByRoomId(Integer roomId);

	public List<EventDetails> findEventByEmpId(String empId);

	public Map<String, List<EventDetails>> getAllEventGroupByRoomName();

	public List<EventDetails> getPastEvents(String empId);

	public List<EventDetails> getFutureEvents(String empId);

	public boolean cancelEvent(String id);

	boolean existsById(String eventId);
	
	String getEventOrganizerId(String eventId);

	public boolean modifyEvent(EventDTO modifiedEvent);

	boolean checkModifiedSlotAvailability(EventDetails event, EventDTO modifiedEvent) throws ParseException;

	public boolean checkSlotAvailability(EventDTO modifiedEvent);
}
