package org.jeavio.meetin.backend.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventDetails {

	private String eventId;
	private String title;
	private String agenda;
	private String roomName;
	private Date start;
	private Date end;
	private UserInfo organizer;
	private List<MemberInfo> members;

	public EventDetails() {
	}

	public EventDetails(String eventId, String title, String agenda, String roomName, Date start, Date end,
			UserInfo organizer, List<MemberInfo> members) {
		this.eventId = eventId;
		this.title = title;
		this.agenda = agenda;
		this.roomName = roomName;
		this.organizer = organizer;
		this.members = members;
		this.start = start;
		this.end =	end;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getStart() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return format.format(start);
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getEnd() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return format.format(end);
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public UserInfo getOrganizer() {
		return organizer;
	}

	public void setOrganizer(UserInfo organizer) {
		this.organizer = organizer;
	}

	public List<MemberInfo> getMembers() {
		return members;
	}

	public void setMembers(List<MemberInfo> members) {
		this.members = members;
	}

}
