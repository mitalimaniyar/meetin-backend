package org.jeavio.meetin.backend.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jeavio.meetin.backend.dao.EventRepository;
import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.dto.MemberInfo;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Event;
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
	EventRepository eventRepository;

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
		if (!checkSlotAvailability(roomName, start, end))
			return false;
		if (start == null || end == null)
			return false;

		UserInfo organizer = userService.findProfileByEmpId(empId);

		List<MemberInfo> participants = new ArrayList<MemberInfo>();
		if (!(newEvent.getTeams()).isEmpty()) {
			for (String teamName : newEvent.getTeams()) {
				if (teamService.existsByTeamName(teamName)) {
					List<UserInfo> users = teamService.findTeamMembers(teamName);
					for (UserInfo user : users) {
						MemberInfo member = new MemberInfo(user, teamName);
						if (!participants.contains(member))
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
		String eventId = roomName + (new SimpleDateFormat("yyyyMMddHHmm").format(start));

		String repeat = newEvent.getRepeat();
		Event event = null;

		event = new Event(eventId, title, agenda, roomName, start, end, organizer, participants);
		eventRepository.save(event);
		notificationService.notifyAll(participants, "create", repeat);

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
				eventId = roomName + (new SimpleDateFormat("yyyyMMddHHmm").format(newStart));
				event = new Event(eventId, title, agenda, roomName, newStart, newEnd, organizer, participants);
				eventRepository.save(event);
			}
			oldStart = newStart;
			oldEnd = newEnd;
			frequency--;
		}
		return true;
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
		List<Event> events = eventRepository.findAllByRoomName(roomName);
		for (Event event : events) {
			Date startDate = event.getStart();
			Date endDate = event.getEnd();

			boolean condition1 = (start.before(startDate) || start.equals(startDate))
					&& (end.after(startDate) || end.equals(startDate));
			boolean condition2 = (start.before(endDate) || start.equals(endDate))
					&& (end.after(endDate) || end.equals(endDate));
			boolean condition3 = (start.after(startDate) || start.equals(startDate))
					&& (end.before(endDate) || end.equals(endDate));
			if (condition1 || condition2 || condition3)
				return false;
		}

		return true;
	}

	@Override
	public boolean checkSlotAvailability(String roomName, String start, String end) {
		if (start == null || end == null)
			return false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date requestedStartDate = null;
		Date requestedEndDate = null;
		try {
			requestedStartDate = format.parse(start);
			requestedEndDate = format.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (requestedStartDate == null || requestedEndDate == null)
			return false;
		else
			return checkSlotAvailability(roomName, requestedStartDate, requestedEndDate);
	}

	@Override
	public boolean existsByEventId(String eventId) {
		return eventRepository.existsByEventId(eventId);
	}

	
	@Override
	public List<EventDetails> findEventByRoomName(String roomName) {
		return eventRepository.findEventDetailsByRoomName(roomName);
	}

	@Override
	public List<EventDetails> findEventByEmpId(String empId) {
		return eventRepository.findEventDetailsByEmpId(empId);
	}

}
