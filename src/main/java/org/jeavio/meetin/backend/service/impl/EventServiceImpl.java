package org.jeavio.meetin.backend.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	@Override
	public boolean addEvent(EventDTO newEvent, String empId) {
		if (!userService.existsByEmpId(empId))
			return false;

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
		if (status)
			notificationService.notifyAll(event, "create", repeat);
		else
			return false;

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
		if (newEvent.getTeams() != null && !(newEvent.getTeams()).isEmpty()) {
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
		if (newEvent.getMembers() != null && !(newEvent.getMembers()).isEmpty()) {
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
		EventDetails event = new EventDetails();
		event.setRoomName(roomName);
		event.setStart(start);
		event.setEnd(end);
		return eventManager.checkSlotAvailability(event);

	}

	@Override
	public boolean checkSlotAvailability(String roomName, String start, String end) {
		try {
			return checkSlotAvailability(roomName, format.parse(start), format.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean existsById(String eventId) {
		return eventManager.existsEvent(eventId);
	}

	@Override
	public List<EventDetails> findEventByRoomId(Integer roomId) {
		if (!roomService.existsByRoomId(roomId))
			return new ArrayList<>();
		String roomName = roomService.findRoomNameById(roomId);
		Map<String, String> requestBody = new LinkedHashMap<String, String>();

		requestBody.put("roomName", roomName);
		List<EventDetails> events = eventManager.getEventByRoomName(requestBody);
		return events;
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
		Map<String, String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("empId", empId);
		return eventManager.getUserPastEvents(requestBody);
	}

	@Override
	public List<EventDetails> getFutureEvents(String empId) {
		if (!userService.existsByEmpId(empId))
			return new ArrayList<EventDetails>();
		Map<String, String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("empId", empId);
		return eventManager.getUserFutureEvents(requestBody);
	}

	@Override
	public boolean cancelEvent(String id) {
		Map<String, String> requestBody = new LinkedHashMap<String, String>();
		requestBody.put("id", id);
		EventDetails event = eventManager.cancelEvent(requestBody);
		if (event != null) {
			notificationService.notifyAll(event, "cancel", null);
			return true;
		}
		return false;
	}

	@Override
	public String getEventOrganizerId(String eventId) {
		if (existsById(eventId)) {
			EventDetails event = eventManager.getEventById(eventId);
			return event.getOrganizer().getEmpId();
		}
		return null;
	}

	@Override
	public boolean modifyEvent(EventDTO modifiedEvent) {
		if (modifiedEvent.getId() == null || !existsById(modifiedEvent.getId())
				|| !roomService.existsByRoomName(modifiedEvent.getRoomName()))
			return false;

		EventDetails event = eventManager.getEventById(modifiedEvent.getId());
		boolean availability = false;
		try {
			availability = checkModifiedSlotAvailability(event, modifiedEvent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!availability)
			return false;

		if (changed(event.getTitle(), modifiedEvent.getTitle()))
			event.setTitle(modifiedEvent.getTitle());
		if (changed(event.getAgenda(), modifiedEvent.getAgenda()))
			event.setAgenda(modifiedEvent.getAgenda());
		if (changed(event.getRoomName(), modifiedEvent.getRoomName())) {
			String roomName = modifiedEvent.getRoomName();
			event.setRoomName(roomName);
			String roomSpecifications = roomService.getRoomSpecifications(roomName);
			event.setRoomSpecifications(roomSpecifications);
		}
		if (changed(format.format(event.getStart()), modifiedEvent.getStart())) {
			try {
				Date newStart = format.parse(modifiedEvent.getStart());
				event.setStart(newStart);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		if (changed(format.format(event.getEnd()), modifiedEvent.getEnd())) {
			try {
				Date newEnd = format.parse(modifiedEvent.getEnd());
				event.setEnd(newEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<MemberInfo> oldMembers = event.getMembers();
		List<MemberInfo> participants = getNewParticipants(event, modifiedEvent);
		event.setMembers(participants);

		boolean status = eventManager.modifyEvent(event);
		if (status) {
			List<String> oldMembersEmails = getOldMembersEmails(event.getMembers(), oldMembers);
			oldMembersEmails.add(event.getOrganizer().getEmail());
			if (!oldMembersEmails.isEmpty())
				notificationService.notifyAll(event, "modify", null, oldMembersEmails);
			
			List<String> removedMembersEmails = getDifferentMembersEmails(oldMembers, event.getMembers());
			if (!removedMembersEmails.isEmpty())
				notificationService.notifyAll(event, "cancel", null, removedMembersEmails);

			List<String> newMembersEmails = getDifferentMembersEmails(event.getMembers(), oldMembers);
			if (!newMembersEmails.isEmpty())
				notificationService.notifyAll(event, "create", null, newMembersEmails);

			return true;
		} else
			return false;
	}

	private List<String> getOldMembersEmails(List<MemberInfo> members, List<MemberInfo> oldMembers) {
		Set<MemberInfo> reatained = members.stream().collect(Collectors.toSet());
		reatained.retainAll(oldMembers);
		List<String> emailIds = reatained.stream().map(member -> member.getEmail()).collect(Collectors.toList());
		return emailIds;
	}

	private List<String> getDifferentMembersEmails(List<MemberInfo> members1, List<MemberInfo> members2) {
		Set<MemberInfo> difference = members1.stream().collect(Collectors.toSet());
		difference.removeAll(members2);
		List<String> emailIds = difference.stream().map(member -> member.getEmail()).collect(Collectors.toList());
		return emailIds;
	}

	private List<MemberInfo> getNewParticipants(EventDetails event, EventDTO modifiedEvent) {
		Set<MemberInfo> newParticipants = getParticipants(modifiedEvent, event.getOrganizer().getEmpId()).stream()
				.collect(Collectors.toSet());
		Set<MemberInfo> existingParticipants = event.getMembers().stream().collect(Collectors.toSet());
		existingParticipants.retainAll(newParticipants);
		existingParticipants.addAll(newParticipants);
		return existingParticipants.stream().collect(Collectors.toList());
	}

	private boolean changed(String value1, String value2) {
		if (value1.equals(value2))
			return false;
		else
			return true;
	}

	@Override
	public boolean checkModifiedSlotAvailability(EventDetails event, EventDTO modifiedEvent) throws ParseException {
		String oldRoom = event.getRoomName();
		String newRoom = modifiedEvent.getRoomName();

		Date oldStart = event.getStart();
		Date oldEnd = event.getEnd();
		Date newStart = format.parse(modifiedEvent.getStart());
		Date newEnd = format.parse(modifiedEvent.getEnd());
		if (newRoom.equals(oldRoom) && newStart.equals(oldStart) && newEnd.equals(oldEnd)) {
			return true;
		} else if (newRoom.equals(oldRoom)) {
			EventDetails dummyEvent = new EventDetails();
			dummyEvent.setId(event.getId());
			dummyEvent.setRoomName(newRoom);
			dummyEvent.setStart(newStart);
			dummyEvent.setEnd(newEnd);
			return eventManager.checkModifiedSlotAvailability(dummyEvent);
		} else {
			if (!roomService.existsByRoomName(newRoom))
				return false;
			return checkSlotAvailability(newRoom, newStart, newEnd);
		}
	}

	@Override
	public boolean checkSlotAvailability(EventDTO modifiedEvent) {
		if (modifiedEvent.getId() == null || !existsById(modifiedEvent.getId())
				|| !roomService.existsByRoomName(modifiedEvent.getRoomName()))
			return false;

		EventDetails event = eventManager.getEventById(modifiedEvent.getId());
		try {
			return checkModifiedSlotAvailability(event, modifiedEvent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

}
