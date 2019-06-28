package org.jeavio.meetin.backend.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.api.EventManagerClient;
import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.dto.MemberInfo;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.service.EventService;
import org.jeavio.meetin.backend.service.NotificationService;
import org.jeavio.meetin.backend.service.RoomService;
import org.jeavio.meetin.backend.service.TeamService;
import org.jeavio.meetin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventManagerClient eventManager;

	@Autowired
	TeamService teamService;

	@Autowired
	RoomService roomService;

	@Autowired
	UserService userService;

	@Autowired
	NotificationService notificationService;

	@Override
	public boolean addEvent(EventDTO newEvent, String empId) {
		if (!userService.existsByEmpId(empId))
			return false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		String title = newEvent.getTitle();
		String agenda = newEvent.getAgenda();
		String roomName = newEvent.getRoomName();
		Date start = null;
		Date end = null;
		try {
			start = format.parse(newEvent.getStart());
			end = format.parse(newEvent.getEnd());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!roomService.existsByRoomName(roomName))
			return false;
		String roomSpecifications = roomService.getRoomSpecifications(roomName);
		if (!checkSlotAvailability(roomName, start, end))
			return false;
		if (start == null || end == null)
			return false;

		UserInfo organizer = getOrganizer(empId);
		List<MemberInfo> participants = getParticipants(newEvent, empId);
		String repeat = newEvent.getRepeat();
		EventDetails event = null;

		event = new EventDetails(title, agenda, roomName, roomSpecifications, start, end, organizer, participants);
		boolean status = eventManager.addEvent(event);
		if(status)
			notificationService.notifyAll(event, "create", repeat);

		int frequency = 0;

		if (repeat == null || repeat.equals("none"))
			frequency = 0;
		else if (repeat.equals("daily"))
			frequency = 60;
		else if (repeat.equals("weekly"))
			frequency = 8;

		Date oldStart = start;
		Date oldEnd = end;

		while (frequency > 0) {
			Date newStart = getNextDate(oldStart, repeat);
			Date newEnd = getNextDate(oldEnd, repeat);
			if (checkSlotAvailability(roomName, newStart, newEnd)) {
				event = new EventDetails(title, agenda, roomName, roomSpecifications, newStart, newEnd, organizer,
						participants);
				eventManager.addEvent(event);
			}
			oldStart = newStart;
			oldEnd = newEnd;
			frequency--;
		}
		return true;
	}

	private UserInfo getOrganizer(String empId) {
		return userService.findProfileByEmpId(empId);
	}

	private List<MemberInfo> getParticipants(EventDTO newEvent, String empId) {

		List<MemberInfo> participants = new ArrayList<MemberInfo>();
		if (!(newEvent.getTeams()).isEmpty()) {
			for (String teamName : newEvent.getTeams()) {
				if (teamService.existsByTeamName(teamName)) {
					List<UserInfo> users = teamService.findTeamMembers(teamName);
					for (UserInfo user : users) {
						MemberInfo member = new MemberInfo(user, teamName);
						if (!empId.equals(member.getEmpId()) && !participants.contains(member))
							participants.add(member);
					}
				}
			}
		}
		if (!(newEvent.getMembers()).isEmpty()) {
			for (String memberEmpId : newEvent.getMembers()) {
				if (userService.existsByEmpId(memberEmpId)) {
					UserInfo user = userService.findProfileByEmpId(memberEmpId);
					MemberInfo member = new MemberInfo(user, null);
					if (!participants.contains(member))
						participants.add(member);
				}
			}
		}
		return participants;
	}

	private Date getNextDate(Date oldDate, String repeat) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(oldDate);
		if (repeat.equals("daily")) {
			calender.add(Calendar.DAY_OF_WEEK, 1);
		} else if (repeat.equals("weekly")) {
			calender.add(Calendar.DAY_OF_WEEK, 7);
		}
		return calender.getTime();
	}

	@Override
	public boolean checkSlotAvailability(String roomName, Date start, Date end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return checkSlotAvailability(roomName, format.format(start), format.format(end));
	}

	@Override
	public boolean checkSlotAvailability(String roomName, String start, String end) {
		Map<String, String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("roomName", roomName);
		requestBody.put("start", start);
		requestBody.put("end", end);
		return eventManager.checkSlotAvailability(requestBody);
	}

	@Override
	public boolean existsById(String eventId) {
		Map<String, String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("id",eventId);
		return eventManager.existEvent(requestBody);
	}

	@Override
	public List<EventDetails> findEventByRoomName(String roomName) {
		return eventManager.getEventByRoomName(roomName);
	}

	@Override
	public List<EventDetails> findEventByEmpId(String empId) {
		Map<String, String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("empId", empId);
		return eventManager.getUserEvents(requestBody);
	}

	@Override
	public Map<String, List<EventDetails>> getAllEventGroupByRoomName() {
		List<String> rooms = roomService.getRoomNames();
		Map<String, List<String>> roomNames = new LinkedHashMap<String, List<String>>();
		roomNames.put("roomNames", rooms);
		return eventManager.getEventsByRooms(roomNames);
	}

	@Override
	public List<EventDetails> getPastEvents(String empId) {
		if (!userService.existsByEmpId(empId))
			return new ArrayList<EventDetails>();
		Map<String,String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("empId", empId);
		return eventManager.getUserPastEvents(requestBody);
	}

	@Override
	public List<EventDetails> getFutureEvents(String empId) {
		if (!userService.existsByEmpId(empId))
			return new ArrayList<EventDetails>();
		Map<String,String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("empId", empId);
		return eventManager.getUserFutureEvents(requestBody);
	}

	@Override
	public void cancelEvent(String id) {
		Map<String,String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("id",id);
		EventDetails event = eventManager.cancelEvent(requestBody);
		if(event!=null)
			notificationService.notifyAll(event, "cancel", null);
	}

}
